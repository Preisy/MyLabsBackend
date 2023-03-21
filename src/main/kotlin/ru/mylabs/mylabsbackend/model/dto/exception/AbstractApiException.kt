package ru.mylabs.mylabsbackend.model.dto.exception

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import org.springframework.http.HttpStatus
import ru.mylabs.mylabsbackend.model.dto.ApiResponse
import java.io.Serializable

@JsonIgnoreProperties("cause", "stackTrace", "suppressed", "localizedMessage")
abstract class AbstractApiException(
    override val status: HttpStatus,
    override val message: String
) : ApiResponse, Throwable()