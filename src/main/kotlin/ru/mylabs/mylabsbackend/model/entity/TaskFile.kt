package ru.mylabs.mylabsbackend.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne

@Entity
@JsonIgnoreProperties("order")
class TaskFile (
    @ManyToOne
    val order: Order,
    @Column(nullable = true, length = 255)
    var filename: String? = null
) : AbstractEntity()
