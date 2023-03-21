package ru.mylabs.mylabsbackend.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
@JsonIgnoreProperties("id")
class LabsQuantity(
    @Column
    var quantity: Long
) : AbstractEntity()