package ru.mylabs.mylabsbackend.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.mylabs.mylabsbackend.model.dto.request.ForgetPasswordRequest
import ru.mylabs.mylabsbackend.model.dto.request.ResetPasswordRequest
import ru.mylabs.mylabsbackend.model.dto.request.SignUpConfirmRequest
import ru.mylabs.mylabsbackend.model.dto.request.SignUpRequest
import ru.mylabs.mylabsbackend.model.dto.response.TokenResponse
import ru.mylabs.mylabsbackend.service.authService.AuthService

@RestController
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/signup/confirm")
    fun confirmSignUp(
        @RequestBody signUpConfirmRequest: SignUpConfirmRequest,
        res: HttpServletResponse
    ): TokenResponse {
        val token = authService.confirmSignUp(signUpConfirmRequest)
        res.addHeader("Authorization", token.token)
        res.addHeader("Access-Control-Expose-Headers", "Authorization")
        return token
    }

    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest) = authService.signUp(signUpRequest)
    @PostMapping("/password/forget")
    fun forgetPassword(@RequestBody request: ForgetPasswordRequest) = authService.forgetPassword(request)
    @PostMapping("/password/reset")
    fun resetPassword(@RequestBody request: ResetPasswordRequest) = authService.resetPassword(request)
}