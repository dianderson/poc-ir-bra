package br.com.bra.processingservice.database.entities

import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import br.com.bra.processingservice.common.enums.ProductEnum
import br.com.bra.processingservice.domains.inputs.RegisterProductReturnInput
import jakarta.persistence.*
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "income_report")
@EntityListeners(AuditingEntityListener::class)
data class IncomeReportEntity(
    @field:EmbeddedId
    val id: IncomeReportPK,
    @field:Column(name = "status")
    @field:Enumerated(EnumType.STRING)
    val status: ProcessingStatusEnum,
    @field:Column(name = "pdf_data")
    val pdfData: String? = null,
    @field:LastModifiedDate
    @field:Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
) {
    constructor(cpf: String, year: Int, product: ProductEnum) : this(
        id = IncomeReportPK(
            cpf = cpf,
            year = year,
            product = product
        ),
        status = ProcessingStatusEnum.CREATED
    )

    constructor(input: RegisterProductReturnInput) : this(
        id = IncomeReportPK(
            cpf = input.cpf,
            year = input.year,
            product = input.product
        ),
        status = input.status,
        pdfData = input.pdfData
    )
}
