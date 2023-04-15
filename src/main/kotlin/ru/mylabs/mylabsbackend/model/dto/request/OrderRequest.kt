package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.Order
import ru.mylabs.mylabsbackend.model.entity.labs.LabType

class OrderRequest(
    var deadline: String?,
    var taskText: String?,
    var executor: String?,
    var type: LabType,
    var promoName: String? = null
) {
    fun asModel(): Order {
        return Order(deadline!!, taskText, executor, type)
    }
}