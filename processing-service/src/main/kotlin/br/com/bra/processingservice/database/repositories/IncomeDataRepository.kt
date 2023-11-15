package br.com.bra.processingservice.database.repositories

import br.com.bra.processingservice.common.enums.ProductEnum
import br.com.bra.processingservice.database.entities.IncomeDataEntity
import br.com.bra.processingservice.database.entities.IncomeDataPK
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IncomeDataRepository : JpaRepository<IncomeDataEntity, IncomeDataPK> {
    fun findAllByIdCpfAndIdYearAndIdIsFinishedAndIdProductIn(
        cpf: String,
        year: Int,
        isFinished: Boolean,
        products: Set<ProductEnum>
    ): List<IncomeDataEntity>
}