package ru.mylabs.mylabsbackend.model.dto.exception

import org.springframework.http.HttpStatus

class FileIsTooBigException : AbstractApiException(
    HttpStatus.BAD_REQUEST,
    "File is too big"
)