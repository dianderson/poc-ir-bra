package br.com.bra.processingservice.domains.usecases

import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
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
    override fun execute(input: ProcessIncomeReportInput): IncomeReportModel? {
        val products = databasePort.findIncomeReportByProducts(input)
        return when {
            products == null -> input.processNewRequest()
            products.incomeData.any { it.status == ProcessingStatusEnum.CREATED } -> null
            else -> products
        }
    }

    private fun ProcessIncomeReportInput.processNewRequest() =
        databasePort.createNewRequest(this)
            .let { kafkaPort.sendToProcessing(this) }
            .let { null }
}