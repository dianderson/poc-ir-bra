package br.com.bra.pdfgeneratorservice.kafka.agora;


import br.com.bra.pdfgeneratorservice.domains.agora.inputs.GenerateAgoraPDFInput;
import br.com.bra.pdfgeneratorservice.domains.agora.resources.GenerateAgoraPDF;
import br.com.bra.pdfgeneratorservice.kafka.common.ExceptionMessagesProducer;
import br.com.bra.pdir.avro.AgoraDataAvro;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class AgoraConsumer {
    private static final Logger logger = LogManager.getLogger(AgoraConsumer.class);
    private final ExceptionMessagesProducer exceptionProducer;
    private final GenerateAgoraPDF generateAgoraPDF;

    @KafkaListener(
            topics = "${kafka-config.topics.agora-data.name}",
            groupId = "${kafka-config.group-id}",
            filter = "successFilter"
    )
    void onListener(ConsumerRecord<String, AgoraDataAvro> message, Acknowledgment ack) {
        try {
            generateAgoraPDF.execute(toInput(message.value()));
            ack.acknowledge();
            logger.info("Message consumed: " + message.value());
        } catch (Exception ex) {
            exceptionProducer.sendErrorMessage(message.value().toString(), ex.toString());
            ack.acknowledge();
            logger.error("Message error: " + message.value() + ex);
        }
    }

    private GenerateAgoraPDFInput toInput(AgoraDataAvro avro) {
        return GenerateAgoraPDFInput.builder()
                .cpf(avro.getCpf())
                .year(avro.getYear())
                .name(avro.getName())
                .baseYear(avro.getBaseYear())
                .baseAmount(new BigDecimal(avro.getBaseAmount()))
                .currentYear(avro.getCurrentYear())
                .currentAmount(new BigDecimal(avro.getCurrentAmount()))
                .build();
    }
}
