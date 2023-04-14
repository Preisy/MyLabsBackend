package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.Property

class LabsQuantityRequest(
    val quantity: String
) {
    fun asModel() = Property("labs_quantity", quantity)
}