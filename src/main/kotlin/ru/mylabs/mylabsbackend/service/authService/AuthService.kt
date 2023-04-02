package ru.mylabs.mylabsbackend.service.authService

import org.springframework.mail.SimpleMailMessage
import ru.mylabs.mylabsbackend.model.dto.request.SignUpRequest
import ru.mylabs.mylabsbackend.model.dto.response.TokenResponse


interface AuthService {
    fun signUp(signUpRequest: SignUpRequest): TokenResponse
    fun sendEmail(email: SimpleMailMessage)
}