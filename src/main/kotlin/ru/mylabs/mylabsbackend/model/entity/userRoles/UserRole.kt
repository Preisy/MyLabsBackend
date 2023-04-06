package ru.mylabs.mylabsbackend.model.entity.userRoles

import jakarta.persistence.Column
import jakarta.persistence.Entity
import ru.mylabs.mylabsbackend.model.entity.AbstractEntity

@Entity
class UserRole(
    @Column
    var name: UserRoleType
) : AbstractEntity()