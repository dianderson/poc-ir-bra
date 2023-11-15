package br.com.bra.processingservice.domains.usecases

import br.com.bra.processingservice.domains.inputs.CreatePdfDataInput
import br.com.bra.processingservice.domains.ports.DatabasePort
import br.com.bra.processingservice.domains.resources.CreatePdfData
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CreatePdfDataImpl(
    private val databasePort: DatabasePort
) : CreatePdfData {
    @Transactional
    override fun execute(input: CreatePdfDataInput) {
        databasePort.createPdfData(input)
            .let { databasePort.removeProduct(input) }
    }
}