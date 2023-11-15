package br.com.bra.processingservice.kafka.settings

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "kafka-config")
data class LocalKafkaProperties(
    var serverUrl: String = "",
    var schemaRegistryUrl: String = "",
    var groupId: String = "",
    var concurrency: Int = 0
)