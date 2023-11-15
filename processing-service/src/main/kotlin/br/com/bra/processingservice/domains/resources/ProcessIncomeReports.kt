package br.com.bra.processingservice.domains.resources

import br.com.bra.processingservice.domains.inputs.ProcessIncomeReportInput
import br.com.bra.processingservice.domains.models.IncomeReportModel

interface ProcessIncomeReports {
    fun execute(input: ProcessIncomeReportInput): IncomeReportModel?
}