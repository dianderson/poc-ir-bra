package br.com.bra.processingservice.domains.inputs

import br.com.bra.processingservice.common.enums.ProductsEnum

data class GetIncomeReportInput(
    val cpf: String,
    val year: Int,
    val products: Set<ProductsEnum>
)