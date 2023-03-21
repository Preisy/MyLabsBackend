package ru.mylabs.mylabsbackend.model.dto.exception

import org.springframework.http.HttpStatus

class BadRequestException(
    message: String
) : AbstractApiException(
    HttpStatus.BAD_REQUEST,
    message
) {
    constructor() : this("Bad Request")
}