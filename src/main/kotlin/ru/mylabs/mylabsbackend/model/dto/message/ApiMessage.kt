package ru.mylabs.mylabsbackend.model.dto.message

import org.springframework.http.HttpStatus
import ru.mylabs.mylabsbackend.model.dto.response.ApiResponse


open class ApiMessage(
    override val status: HttpStatus,
    override val message: String
) : ApiResponse