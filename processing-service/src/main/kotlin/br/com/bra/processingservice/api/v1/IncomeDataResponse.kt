package br.com.bra.processingservice.api.v1

import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import br.com.bra.processingservice.common.enums.ProductEnum
import br.com.bra.processingservice.domains.models.IncomeReportDataModel

data class IncomeDataResponse(
    val product: ProductEnum,
    val status: ProcessingStatusEnum,
    val pdfData: String? = null
) {
    constructor(model: IncomeReportDataModel) : this(
        product = model.product,
        status = model.status,
        pdfData = model.pdfData
    )
}
