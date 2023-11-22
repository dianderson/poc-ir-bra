package br.com.bra.pdfgeneratorservice.kafka.agora;

import br.com.bra.pdfgeneratorservice.domains.agora.models.AgoraPDFModel;
import br.com.bra.pdfgeneratorservice.domains.agora.ports.AgoraKafkaPort;
import br.com.bra.pdir.avro.IncomePDFAvro;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AgoraAdapterProducer implements AgoraKafkaPort {
    private static final Logger logger = LogManager.getLogger(AgoraAdapterProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${kafka-config.topics.agora-pdf.name}")
    private String topicName;
    @Value("${kafka-config.topics.agora-pdf.ttl-in-days}")
    private Long messageTtl;

    @Override
    public void sendAgoraPDF(AgoraPDFModel model) {
        logger.info("Message sending: " + model);
        kafkaTemplate.send(
                buildMessageWithPayload(toAvro(model), model)
        );
    }

    private IncomePDFAvro toAvro(AgoraPDFModel model) {
        return IncomePDFAvro.newBuilder()
                .setCpf(model.getCpf())
                .setYear(model.getYear())
                .setPdfData(model.getPdfData())
                .setStatus(model.getStatus().name())
                .build();
    }

    private Message<IncomePDFAvro> buildMessageWithPayload(IncomePDFAvro avro, AgoraPDFModel model) {
        return MessageBuilder.withPayload(avro)
                .setHeader("version", "1.0.0")
                .setHeader("endOfLife", LocalDate.now().plusDays(messageTtl))
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .setHeader(KafkaHeaders.KEY, model.getCpf() + "-" + model.getYear())
                .build();
    }
}
