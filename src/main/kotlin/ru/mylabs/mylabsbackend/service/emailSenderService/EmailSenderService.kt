package ru.mylabs.mylabsbackend.service.emailSenderService

import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender


interface EmailSenderService {
    fun sendEmail(email: SimpleMailMessage)

}