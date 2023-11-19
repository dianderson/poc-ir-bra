package br.com.bra.processingservice.database.repositories

import br.com.bra.processingservice.common.enums.ProductEnum
import br.com.bra.processingservice.database.entities.IncomeReportEntity
import br.com.bra.processingservice.database.entities.IncomeReportPK
import br.com.bra.processingservice.domains.models.IncomeReportDataModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface IncomeReportRepository : JpaRepository<IncomeReportEntity, IncomeReportPK> {
    @Query(
        value = """
            SELECT new br.com.bra.processingservice.domains.models.IncomeReportDataModel (id.product, status, pdfData) 
            FROM IncomeReportEntity
            WHERE id.cpf = :cpf
            AND id.year = :year
            AND id.product in :products
    """
    )
    fun findAllByFilter(
        cpf: String,
        year: Int,
        products: Set<ProductEnum>
    ): List<IncomeReportDataModel>
}