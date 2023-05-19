package ru.mylabs.mylabsbackend.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import org.springframework.boot.context.properties.bind.DefaultValue

@Entity
class Promocode(
    @Column(nullable = true, length = 255)
    var promoName: String? = null
) : AbstractEntity()
