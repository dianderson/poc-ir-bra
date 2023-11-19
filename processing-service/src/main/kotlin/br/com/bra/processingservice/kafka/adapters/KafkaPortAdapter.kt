package br.com.bra.processingservice.kafka.adapters

import br.com.bra.processingservice.domains.inputs.ProcessIncomeReportInput
import br.com.bra.processingservice.domains.ports.KafkaPort
import br.com.bra.processingservice.kafka.producers.RequestDataProducer
import org.springframework.stereotype.Component

@Component
class KafkaPortAdapter(
    private val requestDataProducer: RequestDataProducer
) : KafkaPort {
    override fun sendToProcessing(input: ProcessIncomeReportInput) {
        requestDataProducer.publish(input)
    }
}