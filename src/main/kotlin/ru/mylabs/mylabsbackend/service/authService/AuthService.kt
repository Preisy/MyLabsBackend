package ru.mylabs.mylabsbackend.service.authService

import org.springframework.mail.SimpleMailMessage

import ru.mylabs.mylabsbackend.model.dto.request.SignUpConfirmRequest
import ru.mylabs.mylabsbackend.model.dto.request.SignUpRequest
import ru.mylabs.mylabsbackend.model.dto.response.TokenResponse
import ru.mylabs.mylabsbackend.model.entity.ConfirmationToken
import ru.mylabs.mylabsbackend.model.entity.User


interface AuthService {
    fun signUp(signUpRequest: SignUpRequest): String
    fun sendEmail(confirmationToken: ConfirmationToken, user: User)
    fun confirmSignUp(confirmRequest: SignUpConfirmRequest): TokenResponse
}