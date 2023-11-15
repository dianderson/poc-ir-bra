package br.com.bra.processservice.domains.models

import jakarta.validation.ConstraintViolationException
import jakarta.validation.Validation
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.hibernate.validator.constraints.br.CPF

data class IncomeReportModel(
    @field:CPF
    val cpf: String,
    @field:Min(2000)
    @field:Max(2023)
    val year: Int
) {
    fun validate(): IncomeReportModel =
        Validation
            .buildDefaultValidatorFactory()
            .validator.validate(this)
            .takeIf { it.isNotEmpty() }
            ?.let { throw ConstraintViolationException(it) }
            ?: this
}
