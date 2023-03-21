package ru.mylabs.mylabsbackend.model.dto.exception

import org.springframework.http.HttpStatus

class FileIsEmptyException : AbstractApiException(
    HttpStatus.BAD_REQUEST,
    "File is empty"
)