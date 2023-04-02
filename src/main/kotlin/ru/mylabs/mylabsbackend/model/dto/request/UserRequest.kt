package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.User

data class UserRequest(
    val email: String, val password: String, val name: String, val contact: String
) : ApiRequest {
    override fun asModel() = User(name, email, password, contact)
}