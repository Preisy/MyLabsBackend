package ru.mylabs.mylabsbackend.service.orderService

import ru.mylabs.mylabsbackend.model.entity.labs.Lab
import ru.mylabs.mylabsbackend.model.entity.Order
import ru.mylabs.mylabsbackend.model.dto.request.OrderRequest
import ru.mylabs.mylabsbackend.model.dto.request.OrderStatusRequest

interface OrderService {
    fun create(orderRequest: OrderRequest): Order
    fun delete(id: Long)
    fun findAll(offset: Int? = null, limit: Int? = null): Iterable<Order>
    fun update(id: Long, orderRequest: OrderRequest): Order
    fun patch(id: Long, orderRequest: OrderRequest): Order
    fun setOrderStatus(id: Long, orderStatusRequest: OrderStatusRequest): Lab
}