package br.com.bra.pocgradle.kafka.settings;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaTopicsSettings {
    private final LocalKafkaProperties properties;
    @Value("${kafka-config.server-url}")
    private String serverUrl;

    @Bean
    CreateTopicsResult createTopic() {
        return AdminClient
                .create(buildKafkaAdmin().getConfigurationProperties())
                .createTopics(buildNewTopics()
                );
    }

    private KafkaAdmin buildKafkaAdmin() {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, serverUrl);
        return new KafkaAdmin(configs);
    }

    private List<NewTopic> buildNewTopics() {
        return properties.getTopics().stream().map(topic ->
                new NewTopic(topic.getName(), topic.getPartitions(), topic.getReplicationFactory().shortValue())
        ).toList();
    }
}
