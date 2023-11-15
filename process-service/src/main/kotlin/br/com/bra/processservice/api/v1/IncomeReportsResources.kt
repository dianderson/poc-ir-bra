package br.com.bra.processservice.api.v1

import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import org.hibernate.validator.constraints.br.CPF
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/income-reports")
class IncomeReportsResources {
    private val logger: Logger = LoggerFactory.getLogger(IncomeReportsResources::class.java)

    @GetMapping("/{cpf}/{year}")
    fun findByCpfAndYear(
        @Valid @NotBlank @CPF @PathVariable cpf: String,
        @Valid @NotNull @Min(2000) @Max(2023) @PathVariable year: Int
    ) {
        logger.info("CPF: $cpf - Year: $year")
    }
}