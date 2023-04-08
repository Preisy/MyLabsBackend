package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.User
import ru.mylabs.mylabsbackend.model.entity.labs.LabType
import ru.mylabs.mylabsbackend.model.entity.labs.UserLab

class UserLabRequest(
    val title: String,
    val duration: Int,
    val price: Int,
    val type: LabType,
    val priority: Int
) {
    fun asModel() = UserLab(title, duration, price, type, priority)
}