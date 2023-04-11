package ru.mylabs.mylabsbackend.model.repository


import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mylabs.mylabsbackend.model.entity.token.EmailConfirmationToken

@Repository
interface EmailConfirmationTokenRepository : CrudRepository<EmailConfirmationToken, Long> {
    fun findByEmail(email: String): EmailConfirmationToken
}