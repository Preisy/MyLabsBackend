package ru.mylabs.mylabsbackend.model.entity.labs

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.Entity
import ru.mylabs.mylabsbackend.model.entity.AbstractEntity

@Entity
@JsonIgnoreProperties("id")
class LabsQuantity(
    @Column
    var quantity: Long
) : AbstractEntity()