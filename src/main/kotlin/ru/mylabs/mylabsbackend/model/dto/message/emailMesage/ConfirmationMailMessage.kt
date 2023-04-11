package ru.mylabs.mylabsbackend.model.dto.message.emailMesage

import org.springframework.mail.SimpleMailMessage

class ConfirmationMailMessage(
    private val subject: String,
    private val text: String,
    private val to: String
) {
    fun asMail(): SimpleMailMessage {
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo(to)
        mailMessage.subject = subject
        mailMessage.from = "MyLabsProgramming@gmail.com"
        mailMessage.text = text
        return mailMessage

    }
}