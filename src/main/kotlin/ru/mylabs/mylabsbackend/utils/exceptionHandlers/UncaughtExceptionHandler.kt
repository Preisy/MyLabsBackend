package ru.mylabs.mylabsbackend.utils.exceptionHandlers

import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.ResponseEntity
import org.springframework.security.access.AccessDeniedException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.mylabs.mylabsbackend.model.dto.exception.AbstractApiException
import ru.mylabs.mylabsbackend.model.dto.exception.ForbiddenException
import ru.mylabs.mylabsbackend.model.dto.exception.InternalServerErrorException
import ru.mylabs.mylabsbackend.model.dto.response.ApiResponse

@RestControllerAdvice
@Order
class UncaughtExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [Throwable::class])
    protected fun handle(
        cause: Throwable,
        request: WebRequest
    ): ResponseEntity<ApiResponse> {
        return InternalServerErrorException().asResponse()
    }
}
