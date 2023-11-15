package br.com.bra.processingservice.api.v1

import br.com.bra.processingservice.common.enums.ProductEnum
import br.com.bra.processingservice.domains.inputs.ProcessIncomeReportInput
import br.com.bra.processingservice.domains.resources.ProcessIncomeReports
import jakarta.validation.Valid
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import org.hibernate.validator.constraints.br.CPF
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/v1/income-reports")
class IncomeReportsResources(
    private val processIncomeReports: ProcessIncomeReports
) {
    private val logger: Logger = LoggerFactory.getLogger(IncomeReportsResources::class.java)

    @GetMapping("/{cpf}/{year}")
    fun findByCpfAndYear(
        @Valid @CPF @PathVariable cpf: String,
        @Valid @Min(2000) @Max(2023) @PathVariable year: Int,
        @RequestParam products: Set<ProductEnum>
    ): ResponseEntity<IncomeReportsResponse> = try {
        ProcessIncomeReportInput(cpf = cpf, year = year, products = products)
            .let { processIncomeReports.execute(it) }
            ?.let { ResponseEntity.status(HttpStatus.OK).body(IncomeReportsResponse(it)) }
            ?: ResponseEntity.status(HttpStatus.NO_CONTENT).build()
    } catch (ex: Exception) {
        logger.error("Error!!!")
        throw ex
    }
}