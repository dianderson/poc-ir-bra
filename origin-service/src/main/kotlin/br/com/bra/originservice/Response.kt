package br.com.bra.originservice

import java.math.BigDecimal

data class Response(
    val cpf: String,
    val name: String,
    val baseYear: Int,
    val baseAmount: BigDecimal,
    val currentYear: Int,
    val currentAmount: BigDecimal
) {
    constructor(cpf: String, year: Int) : this(
        cpf = cpf,
        name = "Nome do Cliente",
        baseYear = year.minus(1),
        baseAmount = BigDecimal.ZERO,
        currentYear = year,
        currentAmount = BigDecimal.TEN
    )
}
