package ru.mylabs.mylabsbackend.configuration

import ru.mylabs.mylabsbackend.model.entity.userRoles.UserRoleType


class RoleHierarchy {
    companion object {
        val hierarchyList: List<UserRoleType> = listOf(UserRoleType.ADMIN, UserRoleType.USER)
        const val hierarchyString: String = "ROLE_ADMIN > ROLE_MODERATOR > ROLE_USER";
    }
}