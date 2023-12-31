package br.com.bra.processingservice.domains.ports

import br.com.bra.processingservice.domains.inputs.ProcessIncomeReportInput

interface KafkaPort {
    fun sendToProcessing(input: ProcessIncomeReportInput)
}