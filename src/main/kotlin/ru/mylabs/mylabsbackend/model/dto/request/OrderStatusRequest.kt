package ru.mylabs.mylabsbackend.model.dto.request

enum class OrderStatus {
    Complete,
}
class OrderStatusRequest(
    val status: OrderStatus?,
    val metadata: LabRequest? = null
)