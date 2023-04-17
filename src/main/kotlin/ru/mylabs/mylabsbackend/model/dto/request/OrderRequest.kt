package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.Order

class OrderRequest(
    var deadline: String?,
    var taskText: String?,
    var executor: String?,
    var type: String,
    var promoName: String? = null
) {
    fun asModel(): Order {
        return Order(deadline!!, taskText, executor, type)
    }
}