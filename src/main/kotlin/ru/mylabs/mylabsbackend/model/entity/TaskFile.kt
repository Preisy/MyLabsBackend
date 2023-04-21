package ru.mylabs.mylabsbackend.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.ManyToOne
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
@JsonIgnoreProperties("order")
class TaskFile(
    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    val order: Order,
    @Column(nullable = true, length = 255)
    var filename: String? = null
) : AbstractEntity()
