package ru.mylabs.mylabsbackend.service.emailSenderService

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
class EmailSenderServiceImpl(
    private val javaMailSender: JavaMailSender
):EmailSenderService {

    override fun sendEmail(email: SimpleMailMessage) {
        javaMailSender.send(email);
    }
}