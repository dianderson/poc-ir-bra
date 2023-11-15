package br.com.bra.processingservice.database.adapters

import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import br.com.bra.processingservice.database.entities.IncomeDataEntity
import br.com.bra.processingservice.database.entities.IncomeDataPK
import br.com.bra.processingservice.database.entities.IncomeRequestEntity
import br.com.bra.processingservice.database.entities.IncomeRequestPK
import br.com.bra.processingservice.database.repositories.IncomeDataRepository
import br.com.bra.processingservice.database.repositories.IncomeRequestRepository
import br.com.bra.processingservice.domains.inputs.CreatePdfDataInput
import br.com.bra.processingservice.domains.inputs.ProcessIncomeReportInput
import br.com.bra.processingservice.domains.models.IncomeDataModel
import br.com.bra.processingservice.domains.models.IncomeRequestModel
import br.com.bra.processingservice.domains.ports.DatabasePort
import jakarta.persistence.EntityNotFoundException
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class DatabasePortAdapter(
    private val incomeDataRepository: IncomeDataRepository,
    private val incomeRequestRepository: IncomeRequestRepository
) : DatabasePort {
    override fun verifyIfExists(cpf: String, year: Int): Boolean =
        incomeRequestRepository.existsById(IncomeRequestPK(cpf, year))

    override fun findAllProcessedIncomeData(input: ProcessIncomeReportInput): List<IncomeDataModel> =
        incomeDataRepository.findAllByIdCpfAndIdYearAndIsFinishedAndIdProductIn(
            cpf = input.cpf,
            year = input.year,
            isFinished = true,
            products = input.products
        ).map { it.toIncomeDataModel() }

    override fun registerRequest(input: ProcessIncomeReportInput) {
        input.buildRegisterEntities()
            .also {
                incomeRequestRepository.save(it.first)
                incomeDataRepository.saveAll(it.second)
            }
    }

    override fun createPdfData(input: CreatePdfDataInput) =
        incomeDataRepository.save(IncomeDataEntity(input))
            .toIncomeDataModel()

    override fun removeProduct(input: CreatePdfDataInput): IncomeRequestModel =
        getIncomeRequestEntity(input.cpf, input.year)
            .removeProduct(input.product)
            .let { incomeRequestRepository.save(it) }
            .toIncomeRequestModel()

    private fun getIncomeRequestEntity(cpf: String, year: Int) =
        incomeRequestRepository.findByIdOrNull(IncomeRequestPK(cpf, year))
            ?: throw EntityNotFoundException("Deveria ter!!!")

    private fun ProcessIncomeReportInput.buildRegisterEntities() =
        IncomeRequestEntity(
            id = IncomeRequestPK(cpf, year),
            awaitingProcessing = products.map { "${it.ref}" }.reduce { a, b -> "$a,$b," }
        ) to products.map {
            IncomeDataEntity(
                id = IncomeDataPK(
                    cpf = cpf,
                    year = year,
                    product = it
                ),
                status = ProcessingStatusEnum.CREATED,
                isFinished = false
            )
        }
}