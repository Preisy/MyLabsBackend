package ru.mylabs.mylabsbackend.model.dto.response

import ru.mylabs.mylabsbackend.model.entity.User

class InvitedUserResponse(
    val invitedUser: User,
    val deductions: Float
)