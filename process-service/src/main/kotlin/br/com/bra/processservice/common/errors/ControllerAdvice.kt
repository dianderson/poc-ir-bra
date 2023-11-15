package br.com.bra.processservice.common.errors

import jakarta.servlet.http.HttpServletRequest
import jakarta.validation.ConstraintViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.reactive.result.method.annotation.ResponseEntityExceptionHandler
import java.time.Instant

@ControllerAdvice
class ControllerAdvice : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ConstraintViolationException::class)
    fun handle(
        ex: ConstraintViolationException,
        request: HttpServletRequest
    ): ResponseEntity<ErrorHandlerModel> =
        ex.constraintViolations.map {
            ErrorMessageData(field = it.propertyPath.toString(), error = it.message)
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

}