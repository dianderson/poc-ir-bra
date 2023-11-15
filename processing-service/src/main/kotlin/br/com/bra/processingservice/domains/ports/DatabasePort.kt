package br.com.bra.processingservice.domains.ports

import br.com.bra.processingservice.domains.inputs.CreatePdfDataInput
import br.com.bra.processingservice.domains.inputs.ProcessIncomeReportInput
import br.com.bra.processingservice.domains.models.IncomeDataModel
import br.com.bra.processingservice.domains.models.IncomeRequestModel

interface DatabasePort {
    fun verifyIfExists(cpf: String, year: Int): Boolean
    fun findAllProcessedIncomeData(input: ProcessIncomeReportInput): List<IncomeDataModel>
    fun registerRequest(input: ProcessIncomeReportInput)
    fun createPdfData(input: CreatePdfDataInput): IncomeDataModel
    fun removeProduct(input: CreatePdfDataInput): IncomeRequestModel
}