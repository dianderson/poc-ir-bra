package br.com.bra.processingservice.database.entities

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Entity
@Table(name = "income_reports")
data class IncomeReportEntity(
    @field:EmbeddedId
    val incomeId: IncomeReportPK,
    @field:Column(name = "awaiting_processing")
    val awaitingProcessing: String
)
