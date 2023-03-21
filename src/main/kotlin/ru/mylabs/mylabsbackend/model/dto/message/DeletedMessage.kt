package ru.mylabs.mylabsbackend.model.dto.message

import org.springframework.http.HttpStatus

class DeletedMessage : ApiMessage(
    HttpStatus.OK,
    "Successfully deleted"
)