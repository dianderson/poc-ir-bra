package br.com.bra.processingservice.database.entities

import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import br.com.bra.processingservice.domains.inputs.CreatePdfDataInput
import br.com.bra.processingservice.domains.models.IncomeDataModel
import jakarta.persistence.*
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "income_data")
@EntityListeners(AuditingEntityListener::class)
data class IncomeDataEntity(
    @field:EmbeddedId
    val id: IncomeDataPK,
    @field:Column(name = "status")
    @field:Enumerated(EnumType.STRING)
    val status: ProcessingStatusEnum? = null,
    @field:Column(name = "is_finished")
    val isFinished: Boolean,
    @field:Column(name = "pdf_data")
    val pdfData: String? = null,
    @field:LastModifiedDate
    @field:Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
) {
    constructor(input: CreatePdfDataInput) : this(
        id = IncomeDataPK(
            cpf = input.cpf,
            year = input.year,
            product = input.product
        ),
        status = input.status,
        pdfData = input.pdfData,
        isFinished = true
    )

    fun toIncomeDataModel() = IncomeDataModel(
        product = id.product,
        isFinished = isFinished,
        status = status ?: ProcessingStatusEnum.PROCESSING_ERROR,
        pdfData = pdfData ?: ""
    )
}
