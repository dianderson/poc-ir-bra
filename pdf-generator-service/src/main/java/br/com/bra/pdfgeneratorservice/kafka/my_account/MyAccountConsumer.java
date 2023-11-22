package br.com.bra.pdfgeneratorservice.kafka.my_account;


import br.com.bra.pdfgeneratorservice.domains.my_account.inputs.GenerateMyAccountPDFInput;
import br.com.bra.pdfgeneratorservice.domains.my_account.resources.GenerateMyAccountPDF;
import br.com.bra.pdfgeneratorservice.kafka.common.ExceptionMessagesProducer;
import br.com.bra.pdir.avro.MyAccountDataAvro;
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
public class MyAccountConsumer {
    private static final Logger logger = LogManager.getLogger(MyAccountConsumer.class);
    private final ExceptionMessagesProducer exceptionProducer;
    private final GenerateMyAccountPDF generateMyAccountPDF;

    @KafkaListener(
            topics = "${kafka-config.topics.my-account-data.name}",
            groupId = "${kafka-config.group-id}",
            filter = "successFilter"
    )
    void onListener(ConsumerRecord<String, MyAccountDataAvro> message, Acknowledgment ack) {
        try {
            generateMyAccountPDF.execute(toInput(message.value()));
            ack.acknowledge();
            logger.info("Message consumed: " + message.value());
        } catch (Exception ex) {
            exceptionProducer.sendErrorMessage(message.value().toString(), ex.toString());
            ack.acknowledge();
            logger.error("Message error: " + message.value() + ex);
        }
    }

    private GenerateMyAccountPDFInput toInput(MyAccountDataAvro avro) {
        return GenerateMyAccountPDFInput.builder()
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
