package ru.mylabs.mylabsbackend.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class Promocode(
    @Column(nullable = true, length = 255)
    var promoName: String? = null
) : AbstractEntity()
