package br.com.bra.integrationservice.kafka.agora;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

@Configuration
public class AgoraConsumerFilter {
    @Bean
    RecordFilterStrategy<String, String> agoraFilter() {
        return rec -> !new String(rec.headers().lastHeader("Products").value()).contains("AGORA");
    }
}
