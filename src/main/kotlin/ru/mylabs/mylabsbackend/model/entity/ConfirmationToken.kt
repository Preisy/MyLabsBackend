package ru.mylabs.mylabsbackend.model.entity

import jakarta.persistence.*
import java.security.Timestamp
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.*


@Entity
class ConfirmationToken(
    @Column(name = "confirmation_token")
    val confirmationToken: String,

    @OneToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    val user: User
) : AbstractEntity() {
    @Column
    val expiryDate: Date = calculateExpiryDate(2)
     fun calculateExpiryDate(expiryTimeInMinutes: Int): Date {
        val cal = Calendar.getInstance()
        cal.time = Date(cal.time.time)
        cal.add(Calendar.MINUTE, expiryTimeInMinutes)
        return Date(cal.time.time)
    }
}