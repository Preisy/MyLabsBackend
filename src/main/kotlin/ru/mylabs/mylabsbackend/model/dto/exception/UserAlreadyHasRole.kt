package ru.mylabs.mylabsbackend.model.dto.exception

import org.springframework.http.HttpStatus

class UserAlreadyHasRole : AbstractApiException(
    HttpStatus.BAD_REQUEST,
    "User already has this role"
)