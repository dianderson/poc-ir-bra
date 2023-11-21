package br.com.bra.processingservice.kafka.producers

import br.com.bra.pdir.avro.RequestDataAvro
import br.com.bra.processingservice.common.enums.ProductEnum
import br.com.bra.processingservice.domains.inputs.ProcessIncomeReportInput
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

@Component
class RequestDataProducer(
    private val kafkaTemplate: KafkaTemplate<String, Objects>,
    @Value("\${kafka-config.topics.request-data.ttl-in-days}")
    private val ttlInDays: Long,
    @Value("\${kafka-config.topics.request-data.name}")
    private val topicName: String
) {
    private val logger: Logger = LoggerFactory.getLogger(RequestDataProducer::class.java)
    fun publish(input: ProcessIncomeReportInput) = input.toAvro()
        .buildMessageWithPayload(input.products)
        .let { kafkaTemplate.send(it) }
        .also { logger.info("Message sent: $it") }

    private fun RequestDataAvro.buildMessageWithPayload(products: Set<ProductEnum>) =
        MessageBuilder.withPayload(this)
            .setHeader("Version", "1.0.0")
            .setHeader("EndOfLife", LocalDateTime.now().plusDays(ttlInDays))
            .setHeader("Products", products.map { it.name })
            .setHeader(KafkaHeaders.TOPIC, topicName)
            .setHeader(KafkaHeaders.KEY, "$cpf-$year")
            .build()

    private fun ProcessIncomeReportInput.toAvro() = RequestDataAvro.newBuilder()
        .setCpf(cpf)
        .setYear(year)
        .build()
}