package ru.mylabs.mylabsbackend.model.entity

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import jakarta.persistence.*


@Entity
@JsonIgnoreProperties("password")
class User(
    @Column(nullable = false, length = 255)
    var name: String,
    @Column(nullable = false, length = 255)
    var password: String,
) : AbstractEntity() {
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    val roles: MutableList<UserRoles> = mutableListOf()
}