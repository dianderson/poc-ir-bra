package br.com.bra.processingservice.common.errors

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import java.time.Instant

@ControllerAdvice
class ControllerAdvice : ResponseEntityExceptionHandler() {
    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(
        ex: ConstraintViolationException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorHandlerModel> =
        ex.constraintViolations.map {
            ErrorMessageData(field = it.propertyPath.last().name, error = it.message)
        }.let {
            ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                    ErrorHandlerModel(
                        timestamp = Instant.now(),
                        httpStatus = HttpStatus.BAD_REQUEST.value(),
                        errors = it.toSet(),
                        path = request.servletPath
                    )
                )
        }

    @ExceptionHandler(MethodArgumentTypeMismatchException::class)
    fun handleMethodArgumentTypeMismatchException(
        ex: MethodArgumentTypeMismatchException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorHandlerModel> =
        ErrorMessageData(field = ex.name, error = ex.localizedMessage)
            .let {
                ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(
                        ErrorHandlerModel(
                            timestamp = Instant.now(),
                            httpStatus = HttpStatus.BAD_REQUEST.value(),
                            errors = setOf(it),
                            path = request.servletPath
                        )
                    )
            }

}