package ru.mylabs.mylabsbackend.model.dto.exception

import org.springframework.http.HttpStatus

class ConfirmationTokenAlreadySent : AbstractApiException(
    HttpStatus.CONFLICT,
    "Confirmation token already sent"
)