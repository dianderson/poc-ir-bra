package br.com.bra.pocgradle.kafka.my_account;

import br.com.bra.pocgradle.avro.MyAccountDataAvro;
import br.com.bra.pocgradle.domains.my_account.MyAccountModel;
import br.com.bra.pocgradle.domains.my_account.MyAccountKafkaPort;
import lombok.RequiredArgsConstructor;
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
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${kafka-config.topics.my-account-data.name}")
    private String topicName;
    @Value("${kafka-config.topics.my-account-data.ttl-in-days}")
    private Long messageTtl;

    @Override
    public void sendMyAccountData(MyAccountModel model) {
        kafkaTemplate.send(buildMessageWithPayload(toAvro(model)));
    }

    private MyAccountDataAvro toAvro(MyAccountModel model) {
        return MyAccountDataAvro.newBuilder()
                .setCpf(model.getCpf())
                .setName(model.getName())
                .setAmout(model.getAmount().toString())
                .build();
    }

    private Message<MyAccountDataAvro> buildMessageWithPayload(MyAccountDataAvro avro) {
        return MessageBuilder.withPayload(avro)
                .setHeader("version", "1.0.0")
                .setHeader("endOfLife", LocalDate.now().plusDays(messageTtl))
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .setHeader(KafkaHeaders.KEY, avro.getCpf())
                .build();
    }
}
