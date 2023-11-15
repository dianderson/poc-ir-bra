package br.com.bra.processingservice.database.adapters

import br.com.bra.processingservice.database.repositories.IncomeDataRepository
import br.com.bra.processingservice.database.repositories.IncomeRequestRepository
import br.com.bra.processingservice.domains.inputs.GetIncomeReportInput
import br.com.bra.processingservice.domains.models.IncomeDataModel
import br.com.bra.processingservice.domains.ports.DatabasePort
import org.springframework.stereotype.Component

@Component
class DatabasePortAdapter(
    private val incomeDataRepository: IncomeDataRepository,
    private val incomeRequestRepository: IncomeRequestRepository
) : DatabasePort {
    override fun findAllProcessedIncomeData(input: GetIncomeReportInput): List<IncomeDataModel> =
        incomeDataRepository.findAllByIdCpfAndIdYearAndIdIsFinishedAndIdProductIn(
            cpf = input.cpf,
            year = input.year,
            isFinished = true,
            products = input.products
        ).map { it.toIncomeDataModel() }
}