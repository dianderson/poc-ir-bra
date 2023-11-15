package br.com.bra.processingservice.kafka.producers

import br.com.bra.processingservice.avro.RequestDataAvro
import br.com.bra.processingservice.common.enums.ProductEnum
import br.com.bra.processingservice.domains.inputs.GetIncomeReportInput
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.time.LocalDate
import java.util.*

@Component
class RequestDataProducer(
    private val kafkaTemplate: KafkaTemplate<String, Objects>,
    @Value("\${kafka-config.topics.request-data.ttl-in-days}")
    private val ttlInDays: Long,
    @Value("\${kafka-config.topics.request-data.name}")
    private val topicName: String
) {
    fun publish(input: GetIncomeReportInput) = input.toAvro()
        .buildMessageWithPayload(input.products)
        .let { kafkaTemplate.send(it) }

    private fun RequestDataAvro.buildMessageWithPayload(products: Set<ProductEnum>) =
        MessageBuilder.withPayload(this)
            .setHeader("version", "1.0.0")
            .setHeader("endOfLife", LocalDate.now().plusDays(ttlInDays))
            .setHeader("products", products)
            .setHeader(KafkaHeaders.TOPIC, topicName)
            .setHeader(KafkaHeaders.KEY, "$cpf-$year")
            .build()

    private fun GetIncomeReportInput.toAvro() = RequestDataAvro.newBuilder()
        .setCpf(cpf)
        .setYear(year)
        .build()
}