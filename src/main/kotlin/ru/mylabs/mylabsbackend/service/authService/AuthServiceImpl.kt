package ru.mylabs.mylabsbackend.service.authService

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.EmailAlreadyTakenException
import ru.mylabs.mylabsbackend.model.dto.request.SignUpRequest
import ru.mylabs.mylabsbackend.model.dto.response.TokenResponse
import ru.mylabs.mylabsbackend.model.repository.UserRepository
import ru.mylabs.mylabsbackend.utils.JwtTokenUtil


@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtTokenUtil: JwtTokenUtil,
    private val passwordEncoder: PasswordEncoder
) : AuthService {
    override fun signUp(signUpRequest: SignUpRequest): TokenResponse {
        if (userRepository.existsByEmail(signUpRequest.email)) throw EmailAlreadyTakenException()
        signUpRequest.password = passwordEncoder.encode(signUpRequest.password)
        val user = userRepository.save(signUpRequest.asModel())
            val token: String = jwtTokenUtil.generateToken(user.email)
        return TokenResponse(token)
    }
}