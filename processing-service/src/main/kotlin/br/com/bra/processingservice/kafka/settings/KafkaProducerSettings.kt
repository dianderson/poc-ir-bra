package br.com.bra.processingservice.kafka.settings

import io.confluent.kafka.serializers.KafkaAvroSerializer
import io.confluent.kafka.serializers.KafkaAvroSerializerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import java.util.*

@Configuration
class KafkaProducerSettings(
    private val kafkaProperties: LocalKafkaProperties
) {
    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, Objects> = KafkaTemplate(producerFactory())
    private fun producerFactory(): ProducerFactory<String, Objects> = hashMapOf<String, Any>()
        .apply {
            this[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = kafkaProperties.serverUrl
            this[ProducerConfig.ACKS_CONFIG] = "all"
            this[ProducerConfig.LINGER_MS_CONFIG] = 10
            this[ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG] = false
            this[ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION] = 2
            this[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
            this[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = KafkaAvroSerializer::class.java
            this[KafkaAvroSerializerConfig.SCHEMA_REGISTRY_URL_CONFIG] = kafkaProperties.schemaRegistryUrl
            this[KafkaAvroSerializerConfig.AUTO_REGISTER_SCHEMAS] = true
        }.let { DefaultKafkaProducerFactory(it) }
}