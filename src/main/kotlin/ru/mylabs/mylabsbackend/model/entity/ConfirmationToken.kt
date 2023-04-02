package ru.mylabs.mylabsbackend.model.entity

import jakarta.persistence.*


@Entity
class ConfirmationToken(

    @Column(name = "confirmation_token")
    val confirmationToken: String,

    @OneToOne(targetEntity = User::class, fetch = FetchType.EAGER)
    @JoinColumn(nullable = false, name = "user_id")
    val user: User
) : AbstractEntity()