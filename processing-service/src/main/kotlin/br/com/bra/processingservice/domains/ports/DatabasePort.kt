package br.com.bra.processingservice.domains.ports

import br.com.bra.processingservice.domains.inputs.GetIncomeReportInput
import br.com.bra.processingservice.domains.models.IncomeDataModel

interface DatabasePort {
    fun findAllProcessedIncomeData(input: GetIncomeReportInput): List<IncomeDataModel>
}