package br.com.bra.pdfgeneratorservice.kafka.common;

import br.com.bra.pdir.avro.ExceptionMessagesAvro;
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
public class ExceptionMessagesProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    @Value("${kafka-config.topics.exception-messages.name}")
    private String topicName;
    @Value("${kafka-config.topics.exception-messages.ttl-in-days}")
    private Long messageTtl;

    public void sendErrorMessage(String message, String error) {
        kafkaTemplate.send(buildMessageWithPayload(toAvro(message, error)));
    }

    private ExceptionMessagesAvro toAvro(String message, String error) {
        return ExceptionMessagesAvro.newBuilder()
                .setMessage(message)
                .setError(error)
                .build();
    }

    private Message<ExceptionMessagesAvro> buildMessageWithPayload(ExceptionMessagesAvro avro) {
        return MessageBuilder.withPayload(avro)
                .setHeader("version", "1.0.0")
                .setHeader("endOfLife", LocalDate.now().plusDays(messageTtl))
                .setHeader(KafkaHeaders.TOPIC, topicName)
                .setHeader(KafkaHeaders.KEY, avro.getMessage())
                .build();
    }
}