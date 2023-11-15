package br.com.bra.processingservice.domains.ports

import br.com.bra.processingservice.domains.inputs.GetIncomeReportInput
import br.com.bra.processingservice.domains.models.IncomeReportModel
import reactor.core.publisher.Mono

interface DatabasePort {
    fun findIncomeReports(input: GetIncomeReportInput): Mono<IncomeReportModel>
}