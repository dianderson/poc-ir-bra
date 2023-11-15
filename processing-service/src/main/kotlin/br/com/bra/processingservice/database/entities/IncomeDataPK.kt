package br.com.bra.processingservice.database.entities

import br.com.bra.processingservice.common.enums.ProductEnum
import jakarta.persistence.*
import java.io.Serializable

@Embeddable
data class IncomeDataPK(
    @field:Column(name = "cpf")
    val cpf: String,
    @field:Column(name = "year")
    val year: Int,
    @field:Column(name = "product")
    @field:Enumerated(EnumType.STRING)
    val product: ProductEnum,
    @field:Column(name = "is_finished")
    val isFinished: Boolean
) : Serializable
