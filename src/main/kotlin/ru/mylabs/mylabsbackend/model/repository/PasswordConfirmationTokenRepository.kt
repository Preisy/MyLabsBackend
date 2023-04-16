package ru.mylabs.mylabsbackend.model.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mylabs.mylabsbackend.model.entity.token.PasswordConfirmationToken

@Repository
interface PasswordConfirmationTokenRepository : CrudRepository<PasswordConfirmationToken, Long> {
    fun findByEmail(email: String): PasswordConfirmationToken
    fun existsByEmail(email: String): Boolean
}