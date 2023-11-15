package br.com.bra.processingservice.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class IncomeRequestPK(
    @field:Column(name = "cpf")
    val cpf: String,
    @field:Column(name = "year")
    val year: Int
) : Serializable
