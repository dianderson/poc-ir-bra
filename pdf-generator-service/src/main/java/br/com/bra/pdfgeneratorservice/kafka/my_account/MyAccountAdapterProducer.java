package br.com.bra.pdfgeneratorservice.kafka.my_account;

import br.com.bra.pdfgeneratorservice.domains.my_account.models.MyAccountPDFModel;
import br.com.bra.pdfgeneratorservice.domains.my_account.ports.MyAccountKafkaPort;
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
public class MyAccountAdapterProducer implements MyAccountKafkaPort {
    private static final Logger logger = LogManager.getLogger(MyAccountAdapterProducer.class);
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${kafka-config.topics.my-account-pdf.name}")
    private String topicName;
    @Value("${kafka-config.topics.my-account-pdf.ttl-in-days}")
    private Long messageTtl;

    @Override
    public void sendMyAccountPDF(MyAccountPDFModel model) {
        logger.info("Message sending: " + model);
        kafkaTemplate.send(
                buildMessageWithPayload(toAvro(model), model)
        );
    }

    private IncomePDFAvro toAvro(MyAccountPDFModel model) {
        return IncomePDFAvro.newBuilder()
                .setCpf(model.getCpf())
                .setYear(model.getYear())
                .setPdfData(model.getPdfData())
                .setStatus(model.getStatus().name())
                .build();
    }

    private Message<IncomePDFAvro> buildMessageWithPayload(IncomePDFAvro avro, MyAccountPDFModel model) {
        return MessageBuilder.withPayload(avro)
                .setHeader("version", "1.0.0")
                .setHeader("endOfLife", LocalDate.now().plusDays(messageTtl))
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .setHeader(KafkaHeaders.KEY, model.getCpf() + "-" + model.getYear())
                .build();
    }
}
