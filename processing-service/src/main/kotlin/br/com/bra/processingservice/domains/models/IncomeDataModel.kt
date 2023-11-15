package br.com.bra.processingservice.domains.models

import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import br.com.bra.processingservice.common.enums.ProductEnum

data class IncomeDataModel(
    val product: ProductEnum,
    val isFinished: Boolean,
    val status: ProcessingStatusEnum,
    val pdfData: String
)
