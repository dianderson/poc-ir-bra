package br.com.bra.processingservice.common.errors

import java.time.Instant

data class ErrorHandlerModel(
    val timestamp: Instant,
    val httpStatus: Int,
    val errors: Set<ErrorMessageData>,
    val path: String
)
