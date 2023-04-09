package ru.mylabs.mylabsbackend.model.repository


import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import ru.mylabs.mylabsbackend.model.entity.ConfirmationToken

@Repository
interface ConfirmationTokenRepository : CrudRepository<ConfirmationToken, Long> {
    fun findByConfirmationToken(confirmationToken: String): ConfirmationToken
}