package br.com.bra.processingservice.domains.inputs

import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import br.com.bra.processingservice.common.enums.ProductEnum

data class CreatePdfDataInput(
    val cpf: String,
    val year: Int,
    val pdfData: String,
    val product: ProductEnum,
    val status: ProcessingStatusEnum
)