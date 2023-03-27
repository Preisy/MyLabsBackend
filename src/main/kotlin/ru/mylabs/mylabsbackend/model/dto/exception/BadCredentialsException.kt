package ru.mylabs.mylabsbackend.model.dto.exception

import org.springframework.http.HttpStatus

class BadCredentialsException : AbstractApiException(
    HttpStatus.UNAUTHORIZED,
    "Bad credentials"
)