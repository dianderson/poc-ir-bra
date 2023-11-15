package br.com.bra.processingservice.kafka.consumers

import br.com.bra.processingservice.avro.IncomePDFAvro
import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import br.com.bra.processingservice.common.enums.ProductEnum
import br.com.bra.processingservice.domains.inputs.CreatePdfDataInput
import br.com.bra.processingservice.domains.resources.CreatePdfData
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class MyAccountPDFConsumer(
    private val createPdfData: CreatePdfData
) {
    private val logger: Logger = LogManager.getLogger(MyAccountPDFConsumer::class.java)

    @KafkaListener(
        topics = ["\${kafka-config.topics.my-account-pdf.name}"],
        groupId = "\${kafka-config.group-id}"
    )
    fun onListener(message: ConsumerRecord<String, IncomePDFAvro>, ack: Acknowledgment) {
        try {
            createPdfData.execute(message.value().toModel())
            logger.info("Message consumed: ${message.value()}")
            ack.acknowledge()
        } catch (ex: Exception) {
            logger.error("Message: ${message.value()} - Error: $ex")
        }
    }

    private fun IncomePDFAvro.toModel() = CreatePdfDataInput(
        cpf = cpf,
        year = year,
        pdfData = pdfData,
        product = ProductEnum.MY_ACCOUNT,
        status = ProcessingStatusEnum.valueOf(status)
    )
}