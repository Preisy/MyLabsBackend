package ru.mylabs.mylabsbackend.model.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class UserRoles(
    @Column
    val role: Roles
) : AbstractEntity()