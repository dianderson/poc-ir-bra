package br.com.bra.processingservice.kafka.consumers

import br.com.bra.processingservice.avro.IncomePDFAvro
import br.com.bra.processingservice.common.enums.ProcessingStatusEnum
import br.com.bra.processingservice.common.enums.ProductEnum
import br.com.bra.processingservice.domains.inputs.RegisterProductReturnInput
import br.com.bra.processingservice.domains.resources.RegisterProductReturn
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.support.Acknowledgment
import org.springframework.stereotype.Component

@Component
class MyAccountDataConsumer(
    private val registerProductReturn: RegisterProductReturn
) {
    private val logger: Logger = LogManager.getLogger(MyAccountDataConsumer::class.java)

    @KafkaListener(
        topics = ["\${kafka-config.topics.my-account-data.name}"],
        groupId = "\${kafka-config.group-id}",
        filter = "notSuccess"
    )
    fun onListener(message: ConsumerRecord<String, IncomePDFAvro>, ack: Acknowledgment) {
        try {
            registerProductReturn.execute(message.value().toModel())
            logger.info("Message consumed: ${message.value()}")
            ack.acknowledge()
        } catch (ex: Exception) {
            logger.error("Message: ${message.value()} - Error: $ex")
        }
    }

    private fun IncomePDFAvro.toModel() = RegisterProductReturnInput(
        cpf = cpf,
        year = year,
        pdfData = null,
        product = ProductEnum.AGORA,
        status = ProcessingStatusEnum.valueOf(status)
    )
}