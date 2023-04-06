package ru.mylabs.mylabsbackend.model.dto.request

import ru.mylabs.mylabsbackend.model.entity.userRoles.UserRole
import ru.mylabs.mylabsbackend.model.entity.userRoles.UserRoleType


class ChangeRoleRequest (
    val name: UserRoleType
) {
     fun asModel() = UserRole(name)
}