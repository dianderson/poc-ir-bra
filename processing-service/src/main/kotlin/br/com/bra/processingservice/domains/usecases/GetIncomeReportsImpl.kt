package br.com.bra.processingservice.domains.usecases

import br.com.bra.processingservice.domains.inputs.GetIncomeReportInput
import br.com.bra.processingservice.domains.models.IncomeReportModel
import br.com.bra.processingservice.domains.ports.DatabasePort
import br.com.bra.processingservice.domains.resources.GetIncomeReports
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class GetIncomeReportsImpl(
    private val port: DatabasePort
) : GetIncomeReports {
    override fun execute(input: GetIncomeReportInput): Mono<IncomeReportModel> =
        port.findIncomeReports(input)
}