package br.com.bra.integrationservice.kafka.my_account;

import br.com.bra.integrationservice.domains.my_account.inputs.MyAccountInput;
import br.com.bra.integrationservice.domains.my_account.resources.ProcessMyAccount;
import br.com.bra.integrationservice.kafka.common.ExceptionMessagesProducer;
import br.com.bra.pdir.avro.RequestDataAvro;
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
    private final ExceptionMessagesProducer exceptionProducer;

    @KafkaListener(
            topics = "${kafka-config.topics.request-data.name}",
            groupId = "${kafka-config.group-id}-$my-account",
            filter = "myAccountFilter"
    )
    void onListener(ConsumerRecord<String, RequestDataAvro> message, Acknowledgment ack) {
        try {
            processMyAccount.execute(toInput(message.value()));
            ack.acknowledge();
            logger.info("Message consumed: " + message.value());
        } catch (Exception ex) {
            exceptionProducer.sendErrorMessage(message.value().toString(), ex.toString());
            ack.acknowledge();
            logger.error("Message error: " + message.value() + ex);
        }
    }

    private MyAccountInput toInput(RequestDataAvro avro) {
        MyAccountInput input = new MyAccountInput();
        input.setCpf(avro.getCpf());
        input.setYear(avro.getYear());
        return input;
    }
}
