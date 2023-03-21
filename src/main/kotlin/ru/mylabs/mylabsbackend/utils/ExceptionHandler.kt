package ru.mylabs.mylabsbackend.utils

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException
import org.slf4j.LoggerFactory
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import ru.mylabs.mylabsbackend.model.dto.ApiResponse
import ru.mylabs.mylabsbackend.model.dto.exception.AbstractApiException
import ru.mylabs.mylabsbackend.model.dto.exception.FileIsTooBigException

@ControllerAdvice
class ExceptionHandler {
    private val logger = LoggerFactory.getLogger(ExceptionHandler::class.java)

    @ExceptionHandler(value = [AbstractApiException::class])
    protected fun handle(cause: AbstractApiException, request: WebRequest): ResponseEntity<ApiResponse> {
        logger.info(cause.stackTraceToString())
        return cause.asResponse()
    }

    @ExceptionHandler(value = [FileSizeLimitExceededException::class])
    protected fun handle(cause: FileSizeLimitExceededException, request: WebRequest): ResponseEntity<ApiResponse> {
        logger.info(cause.stackTraceToString())
        return FileIsTooBigException().asResponse()
    }

//    @ExceptionHandler(value = [Throwable::class])
//    protected fun handle(cause: Throwable, request: WebRequest): ResponseEntity<ApiResponse> {
//        logger.error(cause.stackTraceToString())
//
//        return InternalServerError().asResponse()
//    }
}