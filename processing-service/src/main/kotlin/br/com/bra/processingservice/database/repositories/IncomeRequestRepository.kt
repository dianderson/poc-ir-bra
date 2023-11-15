package br.com.bra.processingservice.database.repositories

import br.com.bra.processingservice.database.entities.IncomeRequestEntity
import br.com.bra.processingservice.database.entities.IncomeRequestPK
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IncomeRequestRepository : JpaRepository<IncomeRequestEntity, IncomeRequestPK>