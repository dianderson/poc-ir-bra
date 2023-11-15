package br.com.bra.processingservice.kafka.settings

import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroDeserializerConfig
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ContainerProperties
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer

@Configuration
class KafkaConsumerSettings(
    private val kafkaProperties: LocalKafkaProperties
) {
    @Bean
    fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Any> =
        ConcurrentKafkaListenerContainerFactory<String, Any>().apply {
            setConcurrency(kafkaProperties.concurrency)
            consumerFactory = consumerFactory()
            containerProperties.ackMode = ContainerProperties.AckMode.MANUAL
        }

    private fun consumerFactory(): ConsumerFactory<String, Any> = hashMapOf<String, Any>().apply {
        this[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.serverUrl
        this[ConsumerConfig.GROUP_ID_CONFIG] = kafkaProperties.groupId
        this[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
        this[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        this[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = KafkaAvroDeserializer::class.java
        this[ErrorHandlingDeserializer.KEY_DESERIALIZER_CLASS] = StringDeserializer::class.java
        this[ErrorHandlingDeserializer.VALUE_DESERIALIZER_CLASS] = KafkaAvroDeserializer::class.java
        this[KafkaAvroDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = kafkaProperties.schemaRegistryUrl
        this[KafkaAvroDeserializerConfig.SPECIFIC_AVRO_READER_CONFIG] = true
        this[KafkaAvroDeserializerConfig.AUTO_REGISTER_SCHEMAS] = false
    }.let { DefaultKafkaConsumerFactory(it) }
}