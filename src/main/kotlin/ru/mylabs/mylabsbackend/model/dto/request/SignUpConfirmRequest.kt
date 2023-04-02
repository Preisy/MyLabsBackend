package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.User


data class SignUpConfirmRequest(
    var code: String
)