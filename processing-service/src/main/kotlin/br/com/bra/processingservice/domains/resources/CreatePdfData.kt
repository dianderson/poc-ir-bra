package br.com.bra.processingservice.domains.resources

import br.com.bra.processingservice.domains.inputs.CreatePdfDataInput

interface CreatePdfData {
    fun execute(input: CreatePdfDataInput)
}