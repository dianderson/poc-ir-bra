package br.com.bra.processingservice.database.entities

import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import br.com.bra.processingservice.domains.inputs.CreatePdfDataInput
import br.com.bra.processingservice.domains.models.IncomeDataModel
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime

@Entity
@Table(name = "income_data")
data class IncomeDataEntity(
    @field:EmbeddedId
    val id: IncomeDataPK,
    @field:Column(name = "status")
    @field:Enumerated(EnumType.STRING)
    val status: ProcessingStatusEnum? = null,
    @field:Column(name = "pdf_data")
    val pdfData: String? = null,
    @field:CreatedDate
    @field:Column(name = "created_at")
    var createdAt: LocalDateTime? = null
) {
    constructor(input: CreatePdfDataInput) : this(
        id = IncomeDataPK(
            cpf = input.cpf,
            year = input.year,
            product = input.product,
            isFinished = true
        ),
        status = input.status,
        pdfData = input.pdfData
    )

    fun toIncomeDataModel() = IncomeDataModel(
        product = id.product,
        isFinished = id.isFinished,
        status = status ?: ProcessingStatusEnum.PROCESSING_ERROR,
        pdfData = pdfData ?: ""
    )
}
