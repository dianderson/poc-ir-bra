package br.com.bra.processingservice.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Embeddable
import java.io.Serializable

@Embeddable
data class IncomeReportPK(
    @field:Column(name = "cpf")
    val cpf: String,
    @field:Column(name = "year")
    val year: String
) : Serializable
