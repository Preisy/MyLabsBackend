package ru.mylabs.mylabsbackend.model.entity.token

import jakarta.persistence.Column
import jakarta.persistence.Entity
import ru.mylabs.mylabsbackend.model.entity.AbstractEntity
import java.util.*

@Entity
class PasswordConfirmationToken(
    @Column(name = "confirmation_token")
    val confirmationToken: String,
    @Column(length = 255, nullable = false)
    var email: String,


    ) : AbstractEntity() {
    @Column
    val expiryDate: Date = calculateExpiryDate(20)
    fun calculateExpiryDate(expiryTimeInMinutes: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = Date(cal.time.time)
        cal.add(Calendar.MINUTE, expiryTimeInMinutes)
        return Date(cal.time.time)
    }

}