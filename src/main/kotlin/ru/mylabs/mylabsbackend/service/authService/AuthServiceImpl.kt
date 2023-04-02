package ru.mylabs.mylabsbackend.service.authService

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.EmailAlreadyTakenException
import ru.mylabs.mylabsbackend.model.dto.exception.ResourceNotFoundException
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
    override fun signUp(signUpRequest: SignUpRequest): String {
        if (userRepository.existsByEmail(signUpRequest.email)) throw EmailAlreadyTakenException()
        signUpRequest.password = passwordEncoder.encode(signUpRequest.password)
        val user = userRepository.save(signUpRequest.asModel())
        val confirmationToken = ConfirmationToken(Random.nextInt(0, 10000).toString(), user)
        confirmationTokenRepository.save(confirmationToken)
        sendEmail(confirmationToken, user)
        return "Check your email to finish registration"
    }

    override fun sendEmail(confirmationToken: ConfirmationToken, user: User) {
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(user.email)
        mailMessage.subject = "Complete Registration!"
        mailMessage.from = "studyforcovid19@gmail.com"
        mailMessage.text = "You know what to do: ${confirmationToken.confirmationToken}"
        javaMailSender.send(mailMessage)
    }

    override fun confirmSignUp(confirmRequest: SignUpConfirmRequest): TokenResponse {
        val confToken = confirmationTokenRepository.findByConfirmationToken(confirmRequest.code)
        val user = userRepository.findByEmail(confToken.user.email).orElseThrow {
            ResourceNotFoundException("User not found")
        }
        val token: String = jwtTokenUtil.generateToken(user.email)
        confirmationTokenRepository.delete(confToken)
        return TokenResponse(token)
    }
}