package br.com.bra.processingservice.domains.resources

import br.com.bra.processingservice.domains.inputs.GetIncomeReportInput
import br.com.bra.processingservice.domains.models.IncomeReportModel

interface GetIncomeReports {
    fun execute(input: GetIncomeReportInput): IncomeReportModel?
}