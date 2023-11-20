package br.com.bra.integrationservice.kafka.agora;

import br.com.bra.integrationservice.domains.agora.inputs.AgoraInput;
import br.com.bra.integrationservice.domains.agora.resources.ProcessAgora;
import br.com.bra.integrationservice.kafka.common.ExceptionMessagesProducer;
import br.com.bra.processingservice.avro.RequestDataAvro;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AgoraConsumer {
    private static final Logger logger = LogManager.getLogger(AgoraConsumer.class);
    private final ProcessAgora processAgora;
    private final ExceptionMessagesProducer exceptionProducer;

    @KafkaListener(
            topics = "${kafka-config.topics.request-data.name}",
            groupId = "${kafka-config.group-id}",
            filter = "agoraFilter"
    )
    void onListener(ConsumerRecord<String, RequestDataAvro> message, Acknowledgment ack) {
        try {
            processAgora.execute(toInput(message.value()));
            ack.acknowledge();
            logger.info("Message consumed: " + message.value());
        } catch (Exception ex) {
            exceptionProducer.sendErrorMessage(message.value().toString(), ex.toString());
            ack.acknowledge();
            logger.error("Message error: " + message.value() + ex);
        }
    }

    private AgoraInput toInput(RequestDataAvro avro) {
        AgoraInput input = new AgoraInput();
        input.setCpf(avro.getCpf());
        input.setYear(avro.getYear());
        return input;
    }
}
