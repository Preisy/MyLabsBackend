package ru.mylabs.mylabsbackend.model.dto.exception

import org.springframework.http.HttpStatus

class ConflictException(
    message: String
) : AbstractApiException(
    HttpStatus.CONFLICT,
    message
)