package br.com.bra.processingservice.database.entities

import br.com.bra.processingservice.common.enums.ProductEnum
import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import java.io.Serializable

@Embeddable
data class IncomeReportPK(
    @field:Column(name = "cpf")
    val cpf: String,
    @field:Column(name = "year")
    val year: Int,
    @field:Column(name = "product")
    @field:Enumerated(EnumType.STRING)
    val product: ProductEnum
) : Serializable
