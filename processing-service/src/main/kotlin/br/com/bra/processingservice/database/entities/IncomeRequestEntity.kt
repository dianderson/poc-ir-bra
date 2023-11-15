package br.com.bra.processingservice.database.entities

import br.com.bra.processingservice.common.enums.ProductEnum
import br.com.bra.processingservice.domains.models.IncomeRequestModel
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@Table(name = "income_request")
@EntityListeners(AuditingEntityListener::class)
data class IncomeRequestEntity(
    @field:EmbeddedId
    val id: IncomeRequestPK,
    @field:Column(name = "awaiting_processing")
    val awaitingProcessing: String? = null,
    @field:CreatedDate
    @field:Column(name = "created_at")
    var createdAt: LocalDateTime? = null,
    @field:LastModifiedDate
    @field:Column(name = "updated_at")
    var updatedAt: LocalDateTime? = null
) {
    fun toIncomeRequestModel() = IncomeRequestModel(
        cpf = id.cpf,
        year = id.year,
        awaitingProcessing
    )

    fun removeProduct(product: ProductEnum): IncomeRequestEntity =
        copy(
            awaitingProcessing = awaitingProcessing?.removePrefix("${product.ref},")?.takeIf { it.isNotEmpty() }
        )
}
