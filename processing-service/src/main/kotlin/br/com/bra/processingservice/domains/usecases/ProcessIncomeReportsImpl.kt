package br.com.bra.processingservice.domains.usecases

import br.com.bra.processingservice.domains.inputs.ProcessIncomeReportInput
import br.com.bra.processingservice.domains.models.IncomeReportModel
import br.com.bra.processingservice.domains.ports.DatabasePort
import br.com.bra.processingservice.domains.ports.KafkaPort
import br.com.bra.processingservice.domains.resources.ProcessIncomeReports
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProcessIncomeReportsImpl(
    private val kafkaPort: KafkaPort,
    private val databasePort: DatabasePort
) : ProcessIncomeReports {
    @Transactional
    override fun execute(input: ProcessIncomeReportInput): IncomeReportModel? = input
        .verifyIfExists()
        ?.verifyIfCompleted()
        ?: run {
            input.registerRequest()
            input.sendToProcess()
            null
        }

    private fun ProcessIncomeReportInput.verifyIfExists() =
        takeIf { databasePort.verifyIfExists(cpf, year) }

    private fun ProcessIncomeReportInput.verifyIfCompleted() =
        databasePort.findAllProcessedIncomeData(this)
            .takeIf { it.size == products.size }
            ?.let { IncomeReportModel(cpf, year, it) }

    private fun ProcessIncomeReportInput.registerRequest() =
        databasePort.registerRequest(this)

    private fun ProcessIncomeReportInput.sendToProcess() =
        kafkaPort.process(this)
}