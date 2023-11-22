package br.com.bra.pdfgeneratorservice.kafka.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

@Configuration
public class DataConsumerSuccessFilter {
    @Bean
    RecordFilterStrategy<String, String> successFilter() {
        return rec -> !new String(rec.headers().lastHeader("Status").value()).contains("SUCCESS");
    }
}
