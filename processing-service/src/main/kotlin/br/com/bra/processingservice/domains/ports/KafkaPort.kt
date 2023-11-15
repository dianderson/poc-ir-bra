package br.com.bra.processingservice.domains.ports

import br.com.bra.processingservice.domains.inputs.GetIncomeReportInput

interface KafkaPort {
    fun process(input: GetIncomeReportInput)
}