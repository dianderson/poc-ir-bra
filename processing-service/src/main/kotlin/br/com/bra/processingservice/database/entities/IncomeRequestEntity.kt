package br.com.bra.processingservice.database.entities

import jakarta.persistence.Column
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity
@Table(name = "income_reports")
data class IncomeRequestEntity(
    @field:EmbeddedId
    val incomeRequestId: IncomeRequestPK,
    @field:Column(name = "awaiting_processing")
    val awaitingProcessing: String? = null,
    @field:CreatedDate
    @field:Column(name = "created_at")
    var createdAt: LocalDateTime,
    @field:LastModifiedDate
    @field:Column(name = "updated_at")
    var updatedAt: LocalDateTime
)
