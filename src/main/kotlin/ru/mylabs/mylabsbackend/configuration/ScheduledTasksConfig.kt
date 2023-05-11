package ru.mylabs.mylabsbackend.configuration

import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled
import ru.mylabs.mylabsbackend.model.repository.EmailConfirmationTokenRepository
import ru.mylabs.mylabsbackend.model.repository.PasswordConfirmationTokenRepository
import java.util.*

@Configuration
@EnableScheduling
class ScheduledTasksConfig(
    private val emailConfirmationTokenRepository: EmailConfirmationTokenRepository,
    private val passwordConfirmationTokenRepository: PasswordConfirmationTokenRepository
) {
    private val logger = LoggerFactory.getLogger(ScheduledTasksConfig::class.java)

    @Scheduled(fixedDelay = 1000)
    fun deleteEmailTokenIfExpired() {
        val cal: Calendar = Calendar.getInstance()
        val tokens = emailConfirmationTokenRepository.findAll()
        tokens.forEach {
            if ((it.expiryDate.time - cal.time.time) <= 0) {
                logger.info("email confirmation token: ${it.email} deleted due to expiration")
                emailConfirmationTokenRepository.delete(it)
            }
        }
    }

    @Scheduled(fixedDelay = 1000)
    fun deletePasswordTokenIfExpired() {
        val cal: Calendar = Calendar.getInstance()
        val tokens = passwordConfirmationTokenRepository.findAll()
        tokens.forEach {
            if ((it.expiryDate.time - cal.time.time) <= 0) {
                logger.info("password confirmation token: ${it.email} deleted due to expiration")
                passwordConfirmationTokenRepository.delete(it)
            }
        }
    }
}