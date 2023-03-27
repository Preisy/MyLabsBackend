package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.User


data class SignUpRequest(
    var email: String,
    var password: String,
    var name: String,
    var contact: String
) : ApiRequest {
    override fun asModel() = User(name, email, password, contact)
}