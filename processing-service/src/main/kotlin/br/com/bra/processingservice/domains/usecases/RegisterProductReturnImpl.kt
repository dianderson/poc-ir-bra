package br.com.bra.processingservice.domains.usecases

import br.com.bra.processingservice.domains.inputs.RegisterProductReturnInput
import br.com.bra.processingservice.domains.ports.DatabasePort
import br.com.bra.processingservice.domains.resources.RegisterProductReturn
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class RegisterProductReturnImpl(
    private val databasePort: DatabasePort
) : RegisterProductReturn {
    @Transactional
    override fun execute(input: RegisterProductReturnInput) {
        databasePort.registerProductReturn(input)
    }
}