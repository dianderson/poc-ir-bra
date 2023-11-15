package br.com.bra.processingservice.domains.ports

import br.com.bra.processingservice.domains.inputs.CreatePdfDataInput
import br.com.bra.processingservice.domains.inputs.GetIncomeReportInput
import br.com.bra.processingservice.domains.models.IncomeDataModel
import br.com.bra.processingservice.domains.models.IncomeRequestModel

interface DatabasePort {
    fun findAllProcessedIncomeData(input: GetIncomeReportInput): List<IncomeDataModel>
    fun createPdfData(input: CreatePdfDataInput): IncomeDataModel
    fun removeProduct(input: CreatePdfDataInput): IncomeRequestModel
}