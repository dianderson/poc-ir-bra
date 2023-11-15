package br.com.bra.processingservice.api.v1

import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import br.com.bra.processingservice.common.enums.ProductEnum
import br.com.bra.processingservice.domains.models.IncomeDataModel

data class IncomeDataResponse(
    val product: ProductEnum,
    val isFinished: Boolean,
    val status: ProcessingStatusEnum,
    val pdfData: String
) {
    constructor(model: IncomeDataModel) : this(
        product = model.product,
        isFinished = model.isFinished,
        status = model.status,
        pdfData = model.pdfData
    )
}
