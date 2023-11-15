package br.com.bra.processingservice.database.entities

import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "income_data")
data class IncomeDataEntity(
    @field:EmbeddedId
    val incomeId: IncomeDataPK,
    @field:Column(name = "status")
    val status: ProcessingStatusEnum,
    @field:Column(name = "pdf_data")
    val pdfData: String
)
