package ru.mylabs.mylabsbackend.model.dto.message.emailMesage

import org.springframework.mail.SimpleMailMessage

class NewOrderMailMessage(
    private val text: String
) {
    fun asMail(): SimpleMailMessage {
        val mailMessage = SimpleMailMessage()
        mailMessage.setTo("MyLabsProgramming@gmail.com")
        mailMessage.subject = "You have a new order!"
        mailMessage.from = "MyLabsProgramming@gmail.com"
        mailMessage.text = text
        return mailMessage
    }
}