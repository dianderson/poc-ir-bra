package br.com.bra.processservice.domains.resources

import br.com.bra.processservice.domains.inputs.GetIncomeReportInput
import br.com.bra.processservice.domains.models.IncomeReportModel

interface GetIncomeReport {
    fun execute(input: GetIncomeReportInput): IncomeReportModel
}