package ru.mylabs.mylabsbackend.model.dto.message

import org.springframework.http.HttpStatus

class UploadedMessage : ApiMessage(
    HttpStatus.OK,
    "File uploaded"
)