package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.labs.UserLab

class UserLabRequest(
    val title: String,
    val price: Int,
    val type: String
) {
    fun asModel() = UserLab(title, price, type)
}