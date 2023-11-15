package br.com.bra.processingservice.kafka.adapters

import br.com.bra.processingservice.domains.inputs.GetIncomeReportInput
import br.com.bra.processingservice.domains.ports.KafkaPort
import br.com.bra.processingservice.kafka.producers.RequestDataProducer
import org.springframework.stereotype.Component

@Component
class KafkaPortAdapter(
    private val requestDataProducer: RequestDataProducer
) : KafkaPort {
    override fun process(input: GetIncomeReportInput) {
        requestDataProducer.publish(input)
    }
}