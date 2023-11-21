package br.com.bra.integrationservice.kafka.agora;

import br.com.bra.integrationservice.common.ProcessingStatusEnum;
import br.com.bra.integrationservice.domains.agora.models.AgoraModel;
import br.com.bra.integrationservice.domains.agora.ports.AgoraKafkaPort;
import br.com.bra.pdir.avro.AgoraDataAvro;
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
    @Value("${kafka-config.topics.agora-data.name}")
    private String topicName;
    @Value("${kafka-config.topics.my-account-data.ttl-in-days}")
    private Long messageTtl;

    @Override
    public void sendAgoraData(AgoraModel model) {
        logger.info("Message sending: " + model);
        kafkaTemplate.send(
                buildMessageWithPayload(toAvro(model), model)
        );
    }

    private AgoraDataAvro toAvro(AgoraModel model) {
        return model.getStatus().equals(ProcessingStatusEnum.SUCCESS)
                ?
                AgoraDataAvro.newBuilder()
                        .setCpf(model.getCpf())
                        .setYear(model.getYear())
                        .setName(model.getAgoraDataModel().getName())
                        .setBaseYear(model.getAgoraDataModel().getBaseYear())
                        .setBaseAmount(model.getAgoraDataModel().getBaseAmount().toString())
                        .setCurrentYear(model.getAgoraDataModel().getCurrentYear())
                        .setCurrentAmount(model.getAgoraDataModel().getCurrentAmount().toString())
                        .build()
                :
                AgoraDataAvro.newBuilder()
                        .setCpf(model.getCpf())
                        .setYear(model.getYear())
                        .setName(null)
                        .setBaseYear(null)
                        .setBaseAmount(null)
                        .setCurrentYear(null)
                        .setCurrentAmount(null)
                        .build();
    }

    private Message<AgoraDataAvro> buildMessageWithPayload(AgoraDataAvro avro, AgoraModel model) {
        return MessageBuilder.withPayload(avro)
                .setHeader("version", "1.0.0")
                .setHeader("endOfLife", LocalDate.now().plusDays(messageTtl))
                .setHeader("Status", model.getStatus().name())
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .setHeader(KafkaHeaders.KEY, model.getCpf() + "-" + model.getYear())
                .build();
    }
}
