package br.com.bra.processingservice.database.adapters

import br.com.bra.processingservice.database.entities.IncomeReportEntity
import br.com.bra.processingservice.database.repositories.IncomeReportRepository
import br.com.bra.processingservice.domains.inputs.ProcessIncomeReportInput
import br.com.bra.processingservice.domains.inputs.RegisterProductReturnInput
import br.com.bra.processingservice.domains.models.IncomeReportModel
import br.com.bra.processingservice.domains.ports.DatabasePort
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class DatabasePortAdapter(
    private val incomeReportRepository: IncomeReportRepository
) : DatabasePort {
    private val logger: Logger = LoggerFactory.getLogger(DatabasePortAdapter::class.java)
    override fun createNewRequest(input: ProcessIncomeReportInput) {
        input.products.map {
            IncomeReportEntity(input.cpf, input.year, it)
        }.let { incomeReportRepository.saveAllAndFlush(it) }
            .also { logger.info("Created: $it") }
    }

    override fun findIncomeReportByProducts(input: ProcessIncomeReportInput): IncomeReportModel? =
        incomeReportRepository.findAllByFilter(input.cpf, input.year, input.products)
            .takeIf { it.isNotEmpty() }
            ?.let {
                IncomeReportModel(
                    cpf = input.cpf,
                    year = input.year,
                    incomeData = it
                )
            }.also {
                logger.info("Find all products: $it")
            }

    override fun registerProductReturn(input: RegisterProductReturnInput) {
        incomeReportRepository.save(IncomeReportEntity(input))
            .also { logger.info("Product registered: $it") }
    }
}