package br.com.bra.processingservice.domains.ports

import br.com.bra.processingservice.domains.inputs.ProcessIncomeReportInput
import br.com.bra.processingservice.domains.inputs.RegisterProductReturnInput
import br.com.bra.processingservice.domains.models.IncomeReportModel

interface DatabasePort {
    fun createNewRequest(input: ProcessIncomeReportInput)
    fun findIncomeReportByProducts(input: ProcessIncomeReportInput): IncomeReportModel?
    fun registerProductReturn(input: RegisterProductReturnInput)
}