package ru.mylabs.mylabsbackend.model.dto.message

import org.springframework.http.HttpStatus

class ConfirmEmailMessage : ApiMessage(
    HttpStatus.OK,
    "Almost done! Check your email for further instructions."
)