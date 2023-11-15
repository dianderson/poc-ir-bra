package br.com.bra.processingservice.api.v1

import br.com.bra.processingservice.domains.models.IncomeReportModel

data class IncomeReportsResponse(
    val cpf: String,
    val year: Int,
    val incomeData: List<IncomeDataResponse>
) {
    constructor(model: IncomeReportModel) : this(
        cpf = model.cpf,
        year = model.year,
        incomeData = model.incomeData.map { IncomeDataResponse(it) }
    )
}
