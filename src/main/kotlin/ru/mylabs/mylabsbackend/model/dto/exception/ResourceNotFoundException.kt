package ru.mylabs.mylabsbackend.model.dto.exception

import org.springframework.http.HttpStatus

class ResourceNotFoundException(
    message: String
) : AbstractApiException(
    HttpStatus.NOT_FOUND,
    message
)