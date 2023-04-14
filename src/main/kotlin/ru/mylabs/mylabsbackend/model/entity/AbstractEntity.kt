package ru.mylabs.mylabsbackend.model.entity

import jakarta.persistence.*
import java.io.Serializable
import java.time.LocalDateTime

@MappedSuperclass
abstract class AbstractEntity : Serializable {
    @Column(nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0
        private set

}