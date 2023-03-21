package ru.mylabs.mylabsbackend.model.dto.exception

import org.springframework.http.HttpStatus

class InternalServerError : AbstractApiException(
    HttpStatus.INTERNAL_SERVER_ERROR,
    "Something went wrong"
)