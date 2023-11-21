package br.com.bra.processingservice.kafka.consumers

import br.com.bra.pdir.avro.AgoraDataAvro
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
class AgoraDataConsumer(
    private val registerProductReturn: RegisterProductReturn
) {
    private val logger: Logger = LogManager.getLogger(AgoraDataConsumer::class.java)

    @KafkaListener(
        topics = ["\${kafka-config.topics.agora-data.name}"],
        groupId = "\${kafka-config.group-id}",
        filter = "notSuccessFilter"
    )
    fun onListener(message: ConsumerRecord<String, AgoraDataAvro>, ack: Acknowledgment) {
        try {
            registerProductReturn.execute(message.value().toModel(message.headers().lastHeader("Status").value()))
            logger.info("Message consumed: ${message.value()}")
            ack.acknowledge()
        } catch (ex: Exception) {
            logger.error("Message: ${message.value()} - Error: $ex")
        }
    }

    private fun AgoraDataAvro.toModel(status: ByteArray) = RegisterProductReturnInput(
        cpf = cpf,
        year = year,
        pdfData = null,
        product = ProductEnum.AGORA,
        status = ProcessingStatusEnum.valueOf(String(status))
    )
}