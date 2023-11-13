package br.com.bra.pocgradle.kafka.consumer;

import br.com.bra.pocgradle.avro.StartCommandAvro;
import br.com.bra.pocgradle.domains.my_account.inputs.MyAccountInput;
import br.com.bra.pocgradle.domains.my_account.usecases.ProcessMyAccount;
import br.com.bra.pocgradle.kafka.producer.ExceptionProducer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MyAccountConsumer {
    private static final Logger logger = LogManager.getLogger(MyAccountConsumer.class);
    private final ProcessMyAccount processMyAccount;
    private final ExceptionProducer exceptionProducer;

    @KafkaListener(
            topics = "${kafka-config.topics[1].name}",
            groupId = "${kafka-config.group-id}",
            filter = "MyAccountFilter"
    )
    void onListener(ConsumerRecord<String, StartCommandAvro> message, Acknowledgment ack) {
        try {
            processMyAccount.execute(toInput(message.value()));
            ack.acknowledge();
            logger.info("Message consumed: " + message.value());
        } catch (Exception ex) {
            exceptionProducer.sendErrorMessage(message.value().toString(), ex.toString());
            logger.error("Message error: " + message);
        }
    }

    private MyAccountInput toInput(StartCommandAvro avro) {
        MyAccountInput input = new MyAccountInput();
        input.setCpf(avro.getCpf());
        input.setYear(avro.getYear());
        return input;
    }
}
