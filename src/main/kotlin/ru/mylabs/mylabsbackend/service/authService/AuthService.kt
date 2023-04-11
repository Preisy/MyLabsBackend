package ru.mylabs.mylabsbackend.service.authService

import ru.mylabs.mylabsbackend.model.dto.message.ConfirmMessage
import ru.mylabs.mylabsbackend.model.dto.request.ForgetPasswordRequest
import ru.mylabs.mylabsbackend.model.dto.request.ResetPasswordRequest
import ru.mylabs.mylabsbackend.model.dto.request.SignUpConfirmRequest
import ru.mylabs.mylabsbackend.model.dto.request.SignUpRequest
import ru.mylabs.mylabsbackend.model.dto.response.TokenResponse


interface AuthService {
    fun signUp(signUpRequest: SignUpRequest): ConfirmMessage
    fun confirmSignUp(confirmRequest: SignUpConfirmRequest): TokenResponse
    fun forgetPassword(request: ForgetPasswordRequest): ConfirmMessage
   fun resetPassword(request: ResetPasswordRequest): TokenResponse
}