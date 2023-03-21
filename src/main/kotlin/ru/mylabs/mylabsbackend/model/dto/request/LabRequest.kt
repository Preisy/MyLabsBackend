package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.Lab
import ru.mylabs.mylabsbackend.model.entity.LabType

class LabRequest (
    val title: String,
    val duration: Int,
    val price: Int,
    val type: LabType,
    val priority: Int
) {
    fun asModel() = Lab(title, duration, price, type, priority)
}