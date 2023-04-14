package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.User

enum class OrderStatus {
    Complete,
}

class OrderStatusRequest(
    val status: OrderStatus?,
    val metadata: UserLabRequest? = null
)