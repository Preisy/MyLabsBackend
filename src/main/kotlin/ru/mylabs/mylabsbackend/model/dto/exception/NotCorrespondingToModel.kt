package ru.mylabs.mylabsbackend.model.dto.exception

class NotCorrespondingToModel(
    override val message: String? = null
) : Exception(message ?: "Not an instance of model")