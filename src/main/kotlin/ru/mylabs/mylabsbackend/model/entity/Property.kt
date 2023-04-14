package ru.mylabs.mylabsbackend.model.entity

import jakarta.persistence.*

@Entity
class Property(
    @Id
    @Column(nullable = false)
    var id: String="",
    @Column(nullable = false)
    var property: String
)