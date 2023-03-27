package ru.mylabs.mylabsbackend.service.orderService

//import java.io.File
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.BadRequestException
import ru.mylabs.mylabsbackend.model.dto.exception.InternalServerErrorException
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.entity.Lab
import ru.mylabs.mylabsbackend.model.entity.Order
import ru.mylabs.mylabsbackend.model.repository.LabRepository
import ru.mylabs.mylabsbackend.model.repository.OrderRepository
import ru.mylabs.mylabsbackend.service.taskFileService.TaskFileService
import ru.mylabs.mylabsbackend.service.taskFileService.TaskFileServiceImpl
import ru.mylabs.mylabsbackend.model.dto.request.OrderRequest
import ru.mylabs.mylabsbackend.model.dto.request.OrderStatus
import ru.mylabs.mylabsbackend.model.dto.request.OrderStatusRequest


@Service
class OrderServiceImpl(
    private val orderRepository: OrderRepository,
    private val labRepository: LabRepository,
    private val taskFileService: TaskFileService
) : OrderService {
    private val logger = LoggerFactory.getLogger(TaskFileServiceImpl::class.java)
    private fun findById(id: Long): Order {
        return orderRepository.findById(id).orElseThrow { ResourceNotFoundException("Order not found") }
    }

    override fun create(orderRequest: OrderRequest): Order {
        try {
            return orderRepository.save(orderRequest.asModel())
        } catch (e: NullPointerException) {
            throw BadRequestException()
        }
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
            username = order.username
            contacts = order.contacts
            deadline = order.deadline
            taskText = order.taskText
            executor = order.executor

            orderRepository.save(this)
        }
    }

    override fun patch(id: Long, orderRequest: OrderRequest): Order {
        return findById(id).apply {
            if (orderRequest.username != null) username = orderRequest.username!!
            if (orderRequest.contacts != null) contacts = orderRequest.contacts!!
            if (orderRequest.deadline != null) deadline = orderRequest.deadline!!
            if (orderRequest.taskText != null) taskText = orderRequest.taskText
            if (orderRequest.executor != null) executor = orderRequest.executor

            orderRepository.save(this)
        }
    }

    override fun setOrderStatus(id: Long, orderStatusRequest: OrderStatusRequest): Lab {
        if (orderStatusRequest.status == OrderStatus.Complete) {
            if (orderStatusRequest.metadata == null)
                throw BadRequestException("Complete order status require Lab info metadata")
            delete(id)
            val model = try {
                orderStatusRequest.metadata.asModel()
            } catch (e: NullPointerException) {
                throw BadRequestException()
            }
            return labRepository.save(model)
        } else {
            throw InternalServerErrorException()
        }
    }
}