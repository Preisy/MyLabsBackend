package ru.mylabs.mylabsbackend.service.orderService

//import java.io.File
import org.slf4j.LoggerFactory
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.BadRequestException
import ru.mylabs.mylabsbackend.model.dto.exception.ConflictException
import ru.mylabs.mylabsbackend.model.dto.exception.InternalServerErrorException
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.dto.message.emailMesage.NewOrderMailMessage
import ru.mylabs.mylabsbackend.model.dto.request.OrderRequest
import ru.mylabs.mylabsbackend.model.dto.request.OrderStatus
import ru.mylabs.mylabsbackend.model.dto.request.OrderStatusRequest
import ru.mylabs.mylabsbackend.model.entity.Order
import ru.mylabs.mylabsbackend.model.entity.labs.UserLab
import ru.mylabs.mylabsbackend.model.repository.OrderRepository
import ru.mylabs.mylabsbackend.model.repository.PromocodeRepository
import ru.mylabs.mylabsbackend.model.repository.UserLabRepository
import ru.mylabs.mylabsbackend.model.repository.UserRepository
import ru.mylabs.mylabsbackend.service.meService.MeService
import ru.mylabs.mylabsbackend.service.taskFileService.TaskFileService
import ru.mylabs.mylabsbackend.service.taskFileService.TaskFileServiceImpl
import ru.mylabs.mylabsbackend.service.userService.UserService
import ru.mylabs.mylabsbackend.utils.validators.dateValidator.DateValidator
import ru.mylabs.mylabsbackend.utils.validators.dateValidator.DateValidatorImpl


@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val userLabRepository: UserLabRepository,
    private val taskFileService: TaskFileService,
    private val userService: UserService,
    private val meService: MeService,
    private val promocodeRepository: PromocodeRepository,
    private val userRepository: UserRepository,
    private val javaMailSender: JavaMailSender
) : OrderService {
    private val logger = LoggerFactory.getLogger(TaskFileServiceImpl::class.java)
    private fun findById(id: Long): Order {
        return orderRepository.findById(id).orElseThrow {
            logger.info("Order not found")
            ResourceNotFoundException("Order")
        }
    }

    private fun findByPromoname(promoName: String) = promocodeRepository.findByPromoName(promoName).orElseThrow {
        ConflictException("Promo-code does not exist")
    }

    override fun create(orderRequest: OrderRequest): Order {
        val model = orderRequest.asModel()
        model.user = meService.getMeInfo()
        val validator: DateValidator = DateValidatorImpl("dd/MM/yyyy")
        if (!validator.isValid(model.deadline)) throw BadRequestException()
        model.promo = orderRequest.promoName
        if (model.promo != null) {
            findByPromoname(model.promo!!)
        }
        val order = orderRepository.save(model)
        logger.info("Order: ${order.id} created by user: ${order.user.id}")
        val text = "Order info:\nid: ${order.id}\ndescription: ${order.taskText}\ndeadline: ${order.deadline}"
        val mailMessage = NewOrderMailMessage(text).asMail()
        javaMailSender.send(mailMessage)
        return order
    }


    override fun delete(id: Long) {
        val order = findById(id)
        var taskFileList = order.taskFiles
        for (task in taskFileList) {
            if (task.filename == null) {
                logger.error("task #${task.id} has null filename")
                throw InternalServerErrorException()
            }
            taskFileService.deleteFileFromStorage(task.filename!!)
        }
        logger.info("Order: $id deleted")
        orderRepository.deleteById(id)
    }

    override fun findAll(offset: Int?, limit: Int?): Iterable<Order> {
        if (orderRepository.count() <= (offset ?: 0) + (limit ?: 0)) return emptyList()
        var res = orderRepository.findAll().drop(offset ?: 0)
        if (limit != null) res = res.dropLast(res.size - limit)
        return res
    }

    override fun update(
        id: Long,
        orderRequest: OrderRequest
    ): Order {
        val order = try {
            orderRequest.asModel()
        } catch (e: NullPointerException) {
            throw BadRequestException()
        }
        return findById(id).apply {
            deadline = order.deadline
            taskText = order.taskText
            executor = order.executor
            type = order.type
            orderRepository.save(this)
        }
    }

    override fun patch(id: Long, orderRequest: OrderRequest): Order {
        return findById(id).apply {
            if (orderRequest.deadline != null) {
                deadline = orderRequest.deadline!!
            }
            if (orderRequest.taskText != null) taskText = orderRequest.taskText
            if (orderRequest.executor != null) executor = orderRequest.executor

            orderRepository.save(this)
        }
    }

    override fun setOrderStatus(id: Long, orderStatusRequest: OrderStatusRequest): UserLab {
        val order = orderRepository.findById(id).orElseThrow {
            logger.info("Order not found")
            ResourceNotFoundException("Order")
        }
        if (orderStatusRequest.status == OrderStatus.Complete) {
            if (orderStatusRequest.metadata == null)
                throw BadRequestException("Complete order status require Lab info metadata")
            delete(id)
            val model = try {
                orderStatusRequest.metadata.asModel()
            } catch (e: NullPointerException) {
                throw BadRequestException()
            }
            model.user = order.user
            if (model.user.invitedById != null) {
                val user = userService.findById(model.user.invitedById!!)
                userService.creditPercent(model.price, user)
                model.user.referralDeductions += userService.calculatePercent(model.price)
                userRepository.save(model.user)
            }
            logger.info("Order: $id completed")
            return userLabRepository.save(model)
        } else {
            logger.error("invalid order status")
            throw InternalServerErrorException()
        }
    }

    override fun findByUserId(id: Long, offset: Int?, limit: Int?): Iterable<Order> {
        if (orderRepository.count() <= (offset ?: 0) + (limit ?: 0)) return emptyList()
        var res = orderRepository.findByUserId(id).drop(offset ?: 0)
        if (limit != null) res = res.dropLast(res.size - limit)
        return res
    }
}