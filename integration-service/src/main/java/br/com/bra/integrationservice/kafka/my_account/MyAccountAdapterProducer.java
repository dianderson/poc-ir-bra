package br.com.bra.integrationservice.kafka.my_account;

import br.com.bra.integrationservice.common.ProcessingStatusEnum;
import br.com.bra.integrationservice.domains.my_account.models.MyAccountModel;
import br.com.bra.integrationservice.domains.my_account.ports.MyAccountKafkaPort;
import br.com.bra.pdir.avro.MyAccountDataAvro;
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
    @Value("${kafka-config.topics.my-account-data.name}")
    private String topicName;
    @Value("${kafka-config.topics.my-account-data.ttl-in-days}")
    private Long messageTtl;

    @Override
    public void sendMyAccountData(MyAccountModel model) {
        logger.info("Message sending: " + model);
        kafkaTemplate.send(
                buildMessageWithPayload(toAvro(model), model)
        );
    }

    private MyAccountDataAvro toAvro(MyAccountModel model) {
        return model.getStatus().equals(ProcessingStatusEnum.SUCCESS)
                ?
                MyAccountDataAvro.newBuilder()
                        .setCpf(model.getCpf())
                        .setYear(model.getYear())
                        .setName(model.getMyAccountDataModel().getName())
                        .setBaseYear(model.getMyAccountDataModel().getBaseYear())
                        .setBaseAmount(model.getMyAccountDataModel().getBaseAmount().toString())
                        .setCurrentYear(model.getMyAccountDataModel().getCurrentYear())
                        .setCurrentAmount(model.getMyAccountDataModel().getCurrentAmount().toString())
                        .build()
                :
                MyAccountDataAvro.newBuilder()
                        .setCpf(model.getCpf())
                        .setYear(model.getYear())
                        .setName(null)
                        .setBaseYear(null)
                        .setBaseAmount(null)
                        .setCurrentYear(null)
                        .setCurrentAmount(null)
                        .build();
    }

    private Message<MyAccountDataAvro> buildMessageWithPayload(MyAccountDataAvro avro, MyAccountModel model) {
        return MessageBuilder.withPayload(avro)
                .setHeader("version", "1.0.0")
                .setHeader("endOfLife", LocalDate.now().plusDays(messageTtl))
                .setHeader("Status", model.getStatus().name())
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .setHeader(KafkaHeaders.KEY, model.getCpf() + "-" + model.getYear())
                .build();
    }
}
