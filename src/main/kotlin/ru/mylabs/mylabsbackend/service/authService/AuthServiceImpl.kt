package ru.mylabs.mylabsbackend.service.authService

import ConfirmationToken
import org.springframework.mail.SimpleMailMessage
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.EmailAlreadyTakenException
import ru.mylabs.mylabsbackend.model.dto.request.SignUpRequest
import ru.mylabs.mylabsbackend.model.dto.response.TokenResponse
import ru.mylabs.mylabsbackend.model.entity.User
import ru.mylabs.mylabsbackend.model.repository.ConfirmationTokenRepository
import ru.mylabs.mylabsbackend.model.repository.UserRepository
import ru.mylabs.mylabsbackend.service.emailSenderService.EmailSenderService
import ru.mylabs.mylabsbackend.utils.JwtTokenUtil
import kotlin.random.Random


@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtTokenUtil: JwtTokenUtil,
    private val passwordEncoder: PasswordEncoder,
    private val confirmationTokenRepository: ConfirmationTokenRepository,
    private val emailSenderService: EmailSenderService
) : AuthService {
    override fun signUp(signUpRequest: SignUpRequest): TokenResponse {
        if (userRepository.existsByEmail(signUpRequest.email)) throw EmailAlreadyTakenException()
        signUpRequest.password = passwordEncoder.encode(signUpRequest.password)
        val user = userRepository.save(signUpRequest.asModel())
            val token: String = jwtTokenUtil.generateToken(user.email)
        val confirmationToken = ConfirmationToken(Random.nextInt(0, 10000).toString(), user)
        //println(confirmationToken.confirmationToken)
        confirmationTokenRepository.save(confirmationToken)
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(user.email)
        mailMessage.setSubject("Complete Registration!")
        mailMessage.setFrom("yakov.komar@mail.ru")
        mailMessage.setText("You know what to do: $confirmationToken")
        emailSenderService.sendEmail(mailMessage)
        return TokenResponse(token)
    }

}