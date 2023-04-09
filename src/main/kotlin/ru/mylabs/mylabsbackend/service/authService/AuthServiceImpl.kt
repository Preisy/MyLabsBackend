package ru.mylabs.mylabsbackend.service.authService

import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.ConfirmationMailSender
import ru.mylabs.mylabsbackend.model.dto.exception.EmailAlreadyTakenException
import ru.mylabs.mylabsbackend.model.dto.exception.TokenExpiredException
import ru.mylabs.mylabsbackend.model.dto.message.ConfirmEmailMessage
import ru.mylabs.mylabsbackend.model.dto.request.SignUpConfirmRequest
import ru.mylabs.mylabsbackend.model.dto.request.SignUpRequest
import ru.mylabs.mylabsbackend.model.dto.response.TokenResponse
import ru.mylabs.mylabsbackend.model.entity.ConfirmationToken
import ru.mylabs.mylabsbackend.model.entity.User
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
        val confirmationToken = ConfirmationToken(
            Random.nextInt(1000, 9999).toString(),
            signUpRequest.name,
            signUpRequest.email,
            signUpRequest.password,
            signUpRequest.contact
        )
        confirmationTokenRepository.save(confirmationToken)
        val confirmationMailSender = ConfirmationMailSender(javaMailSender)
        confirmationMailSender.sendEmail(confirmationToken)
        return ConfirmEmailMessage()
    }

    override fun confirmSignUp(confirmRequest: SignUpConfirmRequest): TokenResponse {
        val confToken = confirmationTokenRepository.findByConfirmationToken(confirmRequest.code)
        val cal: Calendar = Calendar.getInstance()
        if ((confToken.expiryDate.time - cal.time.time) <= 0) {
            confirmationTokenRepository.delete(confToken)
            throw TokenExpiredException()
        }
        val token: String = jwtTokenUtil.generateToken(confToken.email)
        val user = User(confToken.uname, confToken.email, confToken.uPassword, confToken.contact)
        userRepository.save(user)
        confirmationTokenRepository.delete(confToken)
        return TokenResponse(token)
    }
}