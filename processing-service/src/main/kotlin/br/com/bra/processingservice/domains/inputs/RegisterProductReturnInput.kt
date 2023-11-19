package br.com.bra.processingservice.domains.inputs

import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import br.com.bra.processingservice.common.enums.ProductEnum

data class RegisterProductReturnInput(
    val cpf: String,
    val year: Int,
    val pdfData: String? = null,
    val product: ProductEnum,
    val status: ProcessingStatusEnum
)