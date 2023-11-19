package br.com.bra.integrationservice.kafka.agora;

import br.com.bra.integrationservice.avro.AgoraDataAvro;
import br.com.bra.integrationservice.common.ProcessingStatusEnum;
import br.com.bra.integrationservice.domains.agora.models.AgoraModel;
import br.com.bra.integrationservice.domains.agora.ports.AgoraKafkaPort;
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
public class AgoraAdapterProducer implements AgoraKafkaPort {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${kafka-config.topics.my-account-data.name}")
    private String topicName;
    @Value("${kafka-config.topics.my-account-data.ttl-in-days}")
    private Long messageTtl;

    @Override
    public void sendAgoraData(AgoraModel model) {
        kafkaTemplate.send(buildMessageWithPayload(toAvro(model), model.getStatus()));
    }

    private AgoraDataAvro toAvro(AgoraModel model) {
        return AgoraDataAvro.newBuilder()
                .setCpf(model.getCpf())
                .setName(model.getAgoraDataModel().getName())
                .setBaseYear(model.getAgoraDataModel().getBaseYear())
                .setBaseAmount(model.getAgoraDataModel().getBaseAmount().toString())
                .setCurrentYear(model.getAgoraDataModel().getCurrentYear())
                .setCurrentAmount(model.getAgoraDataModel().getCurrentAmount().toString())
                .build();
    }

    private Message<AgoraDataAvro> buildMessageWithPayload(AgoraDataAvro avro, ProcessingStatusEnum status) {
        return MessageBuilder.withPayload(avro)
                .setHeader("version", "1.0.0")
                .setHeader("endOfLife", LocalDate.now().plusDays(messageTtl))
                .setHeader("Status", status)
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .setHeader(KafkaHeaders.KEY, avro.getCpf())
                .build();
    }
}
