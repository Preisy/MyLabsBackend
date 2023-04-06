package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.labs.LabsQuantity

class LabsQuantityRequest(
    val quantity: Long
) {
    fun asModel() = LabsQuantity(quantity)
}