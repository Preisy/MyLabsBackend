package ru.mylabs.mylabsbackend.model.dto

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.io.Serializable


interface ApiResponse : Serializable {
    val status: HttpStatus
    val message: String

    fun asResponse() = ResponseEntity.status(status).body(this)
}