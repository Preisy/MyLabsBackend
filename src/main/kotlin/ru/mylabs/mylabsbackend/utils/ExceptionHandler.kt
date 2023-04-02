package ru.mylabs.mylabsbackend.utils

import org.slf4j.LoggerFactory
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
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    private val logger = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)

    @ExceptionHandler(value = [AbstractApiException::class])
    protected fun handle(
        cause: AbstractApiException,
        request: WebRequest
    ): ResponseEntity<ApiResponse> {
//        logger.info(cause.toString())
        return cause.asResponse()
    }

    @ExceptionHandler(value = [AccessDeniedException::class])
    protected fun handle(
        cause: AccessDeniedException,
        request: WebRequest
    ): ResponseEntity<ApiResponse> {
//        logger.info(cause.toString())
        return ForbiddenException().asResponse()
    }

}