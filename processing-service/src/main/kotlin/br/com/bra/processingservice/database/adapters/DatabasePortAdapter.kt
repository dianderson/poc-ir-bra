package br.com.bra.processingservice.database.adapters

import br.com.bra.processingservice.domains.inputs.GetIncomeReportInput
import br.com.bra.processingservice.domains.models.IncomeReportModel
import br.com.bra.processingservice.domains.ports.DatabasePort
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class DatabasePortAdapter : DatabasePort {
    override fun findIncomeReports(input: GetIncomeReportInput): Mono<IncomeReportModel> {
        TODO("Not yet implemented")
    }
}