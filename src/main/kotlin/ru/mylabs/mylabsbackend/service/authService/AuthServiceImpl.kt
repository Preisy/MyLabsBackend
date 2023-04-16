package ru.mylabs.mylabsbackend.service.authService

import org.springframework.data.repository.findByIdOrNull
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import ru.mylabs.mylabsbackend.model.dto.exception.*
import ru.mylabs.mylabsbackend.model.dto.message.ConfirmMessage
import ru.mylabs.mylabsbackend.model.dto.message.emailMesage.ConfirmationMailMessage
import ru.mylabs.mylabsbackend.model.dto.request.ForgetPasswordRequest
import ru.mylabs.mylabsbackend.model.dto.request.ResetPasswordRequest
import ru.mylabs.mylabsbackend.model.dto.request.SignUpConfirmRequest
import ru.mylabs.mylabsbackend.model.dto.request.SignUpRequest
import ru.mylabs.mylabsbackend.model.dto.response.TokenResponse
import ru.mylabs.mylabsbackend.model.entity.User
import ru.mylabs.mylabsbackend.model.entity.token.EmailConfirmationToken
import ru.mylabs.mylabsbackend.model.entity.token.PasswordConfirmationToken
import ru.mylabs.mylabsbackend.model.repository.EmailConfirmationTokenRepository
import ru.mylabs.mylabsbackend.model.repository.PasswordConfirmationTokenRepository
import ru.mylabs.mylabsbackend.model.repository.UserRepository
import ru.mylabs.mylabsbackend.service.userService.UserService
import ru.mylabs.mylabsbackend.utils.EmailValidator
import ru.mylabs.mylabsbackend.utils.JwtTokenUtil
import java.util.*
import kotlin.random.Random


@Service
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val jwtTokenUtil: JwtTokenUtil,
    private val passwordEncoder: PasswordEncoder,
    private val emailConfirmationTokenRepository: EmailConfirmationTokenRepository,
    private val passwordConfirmationTokenRepository: PasswordConfirmationTokenRepository,
    private val userService: UserService,
    private val javaMailSender: JavaMailSender

) : AuthService {
    private fun emailValidation(email: String): Boolean {
        val regexPattern = "^(.+)@(\\S+)$"
        val emailValidator = EmailValidator(regexPattern)
        return emailValidator.patternMatches(email)
    }
    override fun signUp(signUpRequest: SignUpRequest): ConfirmMessage {
        if (!emailValidation(signUpRequest.email)) throw BadCredentialsException()
        if (userRepository.existsByEmail(signUpRequest.email)) throw EmailAlreadyTakenException()
        signUpRequest.password = passwordEncoder.encode(signUpRequest.password)
        if (emailConfirmationTokenRepository.existsByEmail(signUpRequest.email)) throw ConfirmationTokenAlreadySent()
        val emailConfirmationToken = EmailConfirmationToken(
            Random.nextInt(1000, 9999).toString(),
            signUpRequest.name,
            signUpRequest.email,
            signUpRequest.password,
            signUpRequest.contact
        )
        if (signUpRequest.invitedById != null) {
            var userInvitedBy = userService.findById(signUpRequest.invitedById!!)
            emailConfirmationToken.invitedById = signUpRequest.invitedById
        }
        emailConfirmationTokenRepository.save(emailConfirmationToken)
        val subject = "Complete Registration!"
        val text =
            "Enter the following code: ${emailConfirmationToken.confirmationToken} or just click on the link: https://mylabs.ru/confirm?email=${emailConfirmationToken.email}"
        val mailMessage = ConfirmationMailMessage(subject, text, emailConfirmationToken.email).asMail()
        javaMailSender.send(mailMessage)
        return ConfirmMessage()
    }

    override fun confirmSignUp(confirmRequest: SignUpConfirmRequest): TokenResponse {
        if (!emailValidation(confirmRequest.email)) throw BadCredentialsException()
        val confToken = emailConfirmationTokenRepository.findByEmail(confirmRequest.email)
        if (confToken.confirmationToken == confirmRequest.code) {
            val cal: Calendar = Calendar.getInstance()
            if ((confToken.expiryDate.time - cal.time.time) <= 0) {
                emailConfirmationTokenRepository.delete(confToken)
                throw TokenExpiredException()
            }
            val token: String = jwtTokenUtil.generateToken(confToken.email)
            var user = User(confToken.uname, confToken.email, confToken.uPassword, confToken.contact)
            if (confToken.invitedById != null) {
                user.invitedById = confToken.invitedById
                var userInvitedBy = userService.findById(user.invitedById!!)
                userInvitedBy.invitedUsers?.add(user)
            }
            userRepository.save(user)
            emailConfirmationTokenRepository.delete(confToken)
            return TokenResponse(token)
        } else throw ResourceNotFoundException("Confirmation code")
    }


    override fun forgetPassword(request: ForgetPasswordRequest): ConfirmMessage {
        if (!emailValidation(request.email)) throw BadCredentialsException()
        if (passwordConfirmationTokenRepository.existsByEmail(request.email)) throw ConfirmationTokenAlreadySent()
        val passwordConfToken = PasswordConfirmationToken(Random.nextInt(1000, 9999).toString(), request.email)
        passwordConfirmationTokenRepository.save(passwordConfToken)
        val subject = "Reset your password!"
        val text =
            "Enter the following code: ${passwordConfToken.confirmationToken} or just click on the link: https://mylabs.ru/resetPassword?email=${passwordConfToken.email}"
        val mailMessage = ConfirmationMailMessage(subject, text, passwordConfToken.email).asMail()
        javaMailSender.send(mailMessage)
        return ConfirmMessage()

    }

    override fun resetPassword(request: ResetPasswordRequest): TokenResponse {
        if (!emailValidation(request.email)) throw BadCredentialsException()
        val confToken = passwordConfirmationTokenRepository.findByEmail(request.email)
        if (confToken.confirmationToken == request.code) {
            val cal: Calendar = Calendar.getInstance()
            if ((confToken.expiryDate.time - cal.time.time) <= 0) {
                passwordConfirmationTokenRepository.delete(confToken)
                throw TokenExpiredException()
            }
            val token: String = jwtTokenUtil.generateToken(confToken.email)
            userService.update(request)
            passwordConfirmationTokenRepository.delete(confToken)
            return TokenResponse(token)
        } else throw ResourceNotFoundException("Confirmation code")
    }
}