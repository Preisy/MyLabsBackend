package ru.mylabs.mylabsbackend.model.dto.exception

import org.springframework.http.HttpStatus

class IncorrectFileName : AbstractApiException(
    HttpStatus.BAD_REQUEST,
    "Incorrect file name"
)