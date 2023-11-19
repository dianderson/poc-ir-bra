package br.com.bra.processingservice.domains.resources

import br.com.bra.processingservice.domains.inputs.RegisterProductReturnInput

interface RegisterProductReturn {
    fun execute(input: RegisterProductReturnInput)
}