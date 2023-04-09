package ru.mylabs.mylabsbackend.service.authService

import ru.mylabs.mylabsbackend.model.dto.message.ConfirmEmailMessage
import ru.mylabs.mylabsbackend.model.dto.request.SignUpConfirmRequest
import ru.mylabs.mylabsbackend.model.dto.request.SignUpRequest
import ru.mylabs.mylabsbackend.model.dto.response.TokenResponse


interface AuthService {
    fun signUp(signUpRequest: SignUpRequest): ConfirmEmailMessage
    fun confirmSignUp(confirmRequest: SignUpConfirmRequest): TokenResponse
}