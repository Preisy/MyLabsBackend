package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.UserRole
import ru.mylabs.mylabsbackend.model.entity.UserRoleType


class ChangeRoleRequest (
    val name: UserRoleType
) {
     fun asModel() = UserRole(name)
}