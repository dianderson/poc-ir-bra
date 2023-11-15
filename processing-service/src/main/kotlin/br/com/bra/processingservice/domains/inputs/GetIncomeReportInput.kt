package br.com.bra.processingservice.domains.inputs

import br.com.bra.processingservice.common.enums.ProductEnum

data class GetIncomeReportInput(
    val cpf: String,
    val year: Int,
    val products: Set<ProductEnum>
)