package ru.mylabs.mylabsbackend.model.dto

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import ru.mylabs.mylabsbackend.model.entity.ConfirmationToken

class ConfirmationMailSender(private val javaMailSender: JavaMailSender) : SimpleMailMessage() {

    fun sendEmail(confirmationToken: ConfirmationToken) {
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(confirmationToken.email)
        mailMessage.subject = "Complete Registration!"
        mailMessage.from = "MyLabsProgramming@gmail.com"
        mailMessage.text =
            "Enter the following code: ${confirmationToken.confirmationToken} or just click on the link: https://mylabs.ru/confirm?email=${confirmationToken.email}"
        javaMailSender.send(mailMessage)
    }
}