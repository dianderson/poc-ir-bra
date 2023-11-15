package br.com.bra.processingservice.domains.usecases

import br.com.bra.processingservice.domains.inputs.GetIncomeReportInput
import br.com.bra.processingservice.domains.models.IncomeReportModel
import br.com.bra.processingservice.domains.ports.DatabasePort
import br.com.bra.processingservice.domains.ports.KafkaPort
import br.com.bra.processingservice.domains.resources.GetIncomeReports
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class GetIncomeReportsImpl(
    private val kafkaPort: KafkaPort,
    private val databasePort: DatabasePort
) : GetIncomeReports {
    @Transactional(readOnly = true)
    override fun execute(input: GetIncomeReportInput): IncomeReportModel? =
        databasePort.findAllProcessedIncomeData(input)
            .takeIf { it.size == input.products.size }
            .also { it ?: kafkaPort.process(input) }
            ?.let { IncomeReportModel(input.cpf, input.year, it) }
}