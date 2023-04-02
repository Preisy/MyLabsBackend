package ru.mylabs.mylabsbackend.model.repository


import ru.mylabs.mylabsbackend.model.entity.ConfirmationToken
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ConfirmationTokenRepository : CrudRepository<ConfirmationToken, Long> {
    fun findByConfirmationToken(confirmationToken: String): ConfirmationToken
}