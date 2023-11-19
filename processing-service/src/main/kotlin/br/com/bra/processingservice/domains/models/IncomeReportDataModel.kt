package br.com.bra.processingservice.domains.models

import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import br.com.bra.processingservice.common.enums.ProductEnum

data class IncomeReportDataModel(
    val product: ProductEnum,
    val status: ProcessingStatusEnum,
    val pdfData: String? = null
)
