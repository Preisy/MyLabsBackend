package ru.mylabs.mylabsbackend.model.dto

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import ru.mylabs.mylabsbackend.model.entity.ConfirmationToken
import ru.mylabs.mylabsbackend.model.entity.User

class ConfirmationMailSender(private val javaMailSender: JavaMailSender): SimpleMailMessage() {

     fun sendEmail(confirmationToken: ConfirmationToken, user: User) {
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(user.email)
        mailMessage.subject = "Complete Registration!"
        mailMessage.from = "MyLabsProgramming@gmail.com"
        mailMessage.text = "You know what to do: ${confirmationToken.confirmationToken}"
        javaMailSender.send(mailMessage)
    }
}