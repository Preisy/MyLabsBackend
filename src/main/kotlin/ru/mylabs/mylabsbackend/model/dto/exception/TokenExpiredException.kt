package ru.mylabs.mylabsbackend.model.dto.exception

import org.springframework.http.HttpStatus

class TokenExpiredException: AbstractApiException(
HttpStatus.BAD_REQUEST,
    "Token already expired"
)