package ru.mylabs.mylabsbackend.service.orderService

//import java.io.File
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.BadRequestException
import ru.mylabs.mylabsbackend.model.dto.exception.InternalServerErrorException
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.dto.request.OrderRequest
import ru.mylabs.mylabsbackend.model.dto.request.OrderStatus
import ru.mylabs.mylabsbackend.model.dto.request.OrderStatusRequest
import ru.mylabs.mylabsbackend.model.entity.Order
import ru.mylabs.mylabsbackend.model.entity.labs.UserLab
import ru.mylabs.mylabsbackend.model.repository.OrderRepository
import ru.mylabs.mylabsbackend.model.repository.UserLabRepository
import ru.mylabs.mylabsbackend.service.meService.MeService
import ru.mylabs.mylabsbackend.service.taskFileService.TaskFileService
import ru.mylabs.mylabsbackend.service.taskFileService.TaskFileServiceImpl
import ru.mylabs.mylabsbackend.service.userService.UserService
import ru.mylabs.mylabsbackend.utils.dateValidator.DateValidator
import ru.mylabs.mylabsbackend.utils.dateValidator.DateValidatorImpl


@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val userLabRepository: UserLabRepository,
    private val taskFileService: TaskFileService,
    private val userService: UserService,
    private val meService: MeService,
) : OrderService {
    private val logger = LoggerFactory.getLogger(TaskFileServiceImpl::class.java)
    private fun findById(id: Long): Order {
        return orderRepository.findById(id).orElseThrow { ResourceNotFoundException("Order") }
    }

    override fun create(orderRequest: OrderRequest): Order {
        val model = orderRequest.asModel()
        model.user = meService.getMeInfo()
        val validator: DateValidator = DateValidatorImpl("dd/MM/yyyy")
        if (!validator.isValid(model.deadline)) throw BadRequestException()
        return orderRepository.save(model)
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

        if (orderStatusRequest.status == OrderStatus.Complete) {
            if (orderStatusRequest.metadata == null)
                throw BadRequestException("Complete order status require Lab info metadata")
            delete(id)
            val model = try {
                orderStatusRequest.metadata.asModel()
            } catch (e: NullPointerException) {
                throw BadRequestException()
            }
            model.user = meService.getMeInfo()
            if (model.user.invitedById != null) {
                val user = userService.findById(model.user.invitedById!!)
                userService.creditPercent(model.price, user)
            }
            return userLabRepository.save(model)
        } else {
            throw InternalServerErrorException()
        }
    }

    override fun findByUserId(offset: Int?, limit: Int?): Iterable<Order> {
        if (orderRepository.count() <= (offset ?: 0) + (limit ?: 0)) return emptyList()
        val user = meService.getMeInfo()
        var res = orderRepository.findByUserId(user.id).drop(offset ?: 0)
        if (limit != null) res = res.dropLast(res.size - limit)
        return res
    }
}