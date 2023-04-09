package ru.mylabs.mylabsbackend.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.util.*


@Entity
@JsonIgnoreProperties("upassword", "password")
class ConfirmationToken(
    @Column(name = "confirmation_token")
    val confirmationToken: String,

    @Column(name = "name", length = 255, nullable = false)
    var uname: String,
    @Column(length = 255, nullable = false)
    var email: String,
    @Column(name = "password", length = 255, nullable = false)
    var uPassword: String,
    @Column(length = 255, nullable = false)
    var contact: String,


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