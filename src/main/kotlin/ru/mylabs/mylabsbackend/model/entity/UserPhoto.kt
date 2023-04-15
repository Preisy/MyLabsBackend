package ru.mylabs.mylabsbackend.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToOne

@Entity
@JsonIgnoreProperties("user")
class UserPhoto(
    @OneToOne
    val user: User,
    @Column(nullable = true, length = 255)
    var filename: String? = null
) : AbstractEntity()
