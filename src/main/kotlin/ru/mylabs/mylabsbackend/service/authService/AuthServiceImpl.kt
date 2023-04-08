package ru.mylabs.mylabsbackend.service.authService

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.ConfirmationMailSender
import ru.mylabs.mylabsbackend.model.dto.exception.EmailAlreadyTakenException
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
import ru.mylabs.mylabsbackend.model.dto.exception.TokenExpiredException
import ru.mylabs.mylabsbackend.model.dto.message.ConfirmEmailMessage
import ru.mylabs.mylabsbackend.model.dto.request.SignUpConfirmRequest
import ru.mylabs.mylabsbackend.model.dto.request.SignUpRequest
import ru.mylabs.mylabsbackend.model.dto.response.TokenResponse
import ru.mylabs.mylabsbackend.model.entity.ConfirmationToken

import ru.mylabs.mylabsbackend.model.repository.ConfirmationTokenRepository
import ru.mylabs.mylabsbackend.model.repository.UserRepository
import ru.mylabs.mylabsbackend.utils.JwtTokenUtil
import java.util.*
import kotlin.random.Random


@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtTokenUtil: JwtTokenUtil,
    private val passwordEncoder: PasswordEncoder,
    private val confirmationTokenRepository: ConfirmationTokenRepository,
    private val javaMailSender: JavaMailSender

) : AuthService {
    override fun signUp(signUpRequest: SignUpRequest): ConfirmEmailMessage {
        if (userRepository.existsByEmail(signUpRequest.email)) throw EmailAlreadyTakenException()
        signUpRequest.password = passwordEncoder.encode(signUpRequest.password)
        val user = userRepository.save(signUpRequest.asModel())
        val confirmationToken = ConfirmationToken(UUID.randomUUID().toString(), user)
        confirmationTokenRepository.save(confirmationToken)
        val confirmationMailSender = ConfirmationMailSender(javaMailSender)
        confirmationMailSender.sendEmail(confirmationToken, user)
        return ConfirmEmailMessage()
    }

    override fun confirmSignUp(confirmRequest: SignUpConfirmRequest): TokenResponse {
        val confToken = confirmationTokenRepository.findByConfirmationToken(confirmRequest.code)
        val user = userRepository.findByEmail(confToken.user.email).orElseThrow {
            ResourceNotFoundException("User not found")
        }
         val cal: Calendar = Calendar.getInstance()
        if ((confToken.expiryDate.time - cal.time.time) <= 0) {
            confirmationTokenRepository.delete(confToken)
            userRepository.delete(user)
            throw TokenExpiredException()
        }
        val token: String = jwtTokenUtil.generateToken(user.email)
        confirmationTokenRepository.delete(confToken)
        return TokenResponse(token)
    }
}