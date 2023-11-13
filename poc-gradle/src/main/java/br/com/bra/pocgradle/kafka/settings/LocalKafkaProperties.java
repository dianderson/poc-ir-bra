package br.com.bra.pocgradle.kafka.settings;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "kafka-config")
public class LocalKafkaProperties {
    private String serverUrl;
    private String schemaRegistryUrl;
    private String groupId;
    private Integer concurrency;
}
