package ru.mylabs.mylabsbackend.controller

import jakarta.servlet.http.HttpServletResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import ru.mylabs.mylabsbackend.model.dto.request.SignUpRequest
import ru.mylabs.mylabsbackend.model.dto.response.TokenResponse
import ru.mylabs.mylabsbackend.service.authService.AuthService

@RestController
class AuthController(
    private val authService: AuthService
) {
    @PostMapping("/signup")
    fun signUp(@RequestBody signUpRequest: SignUpRequest, res: HttpServletResponse): TokenResponse {
        val token = authService.signUp(signUpRequest)
        res.addHeader("Authorization", token.token)
        res.addHeader("Access-Control-Expose-Headers", "Authorization")
        return token
    }
}