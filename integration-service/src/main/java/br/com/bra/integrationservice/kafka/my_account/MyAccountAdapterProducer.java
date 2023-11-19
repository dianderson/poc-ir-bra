package br.com.bra.integrationservice.kafka.my_account;

import br.com.bra.integrationservice.avro.MyAccountDataAvro;
import br.com.bra.integrationservice.common.ProcessingStatusEnum;
import br.com.bra.integrationservice.domains.my_account.models.MyAccountModel;
import br.com.bra.integrationservice.domains.my_account.ports.MyAccountKafkaPort;
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
        kafkaTemplate.send(buildMessageWithPayload(toAvro(model), model.getStatus()));
    }

    private MyAccountDataAvro toAvro(MyAccountModel model) {
        return MyAccountDataAvro.newBuilder()
                .setCpf(model.getCpf())
                .setName(model.getMyAccountDataModel().getName())
                .setAmout(model.getMyAccountDataModel().getAmount().toString())
                .build();
    }

    private Message<MyAccountDataAvro> buildMessageWithPayload(MyAccountDataAvro avro, ProcessingStatusEnum status) {
        return MessageBuilder.withPayload(avro)
                .setHeader("version", "1.0.0")
                .setHeader("endOfLife", LocalDate.now().plusDays(messageTtl))
                .setHeader("Status", status)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .setHeader(KafkaHeaders.KEY, avro.getCpf())
                .build();
    }
}
