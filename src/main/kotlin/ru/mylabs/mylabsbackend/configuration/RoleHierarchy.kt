package ru.mylabs.mylabsbackend.configuration

import ru.mylabs.mylabsbackend.model.entity.UserRoleType


class RoleHierarchy {
    companion object {
        val hierarchyList: List<UserRoleType> = listOf(UserRoleType.ADMIN, UserRoleType.MODERATOR, UserRoleType.USER)
        const val hierarchyString: String = "ROLE_ADMIN > ROLE_MODERATOR > ROLE_USER";
    }
}