package ru.mylabs.mylabsbackend.model.dto.request

data class ResetPasswordRequest(
    var code: String,
    var email: String,
    var newPassword: String
)