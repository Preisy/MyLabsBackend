package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.labs.Lab

class LabRequest(
    val title: String,
    val duration: Int,
    val price: Int,
    val type: String,
    val priority: Int
) {
    fun asModel() = Lab(title, duration, price, type, priority)
}