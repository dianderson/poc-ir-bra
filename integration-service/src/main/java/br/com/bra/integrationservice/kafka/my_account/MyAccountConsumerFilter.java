package br.com.bra.integrationservice.kafka.my_account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

@Configuration
public class MyAccountConsumerFilter {
    @Bean
    RecordFilterStrategy<String, String> myAccountFilter() {
        return rec -> !new String(rec.headers().lastHeader("Products").value()).contains("MY_ACCOUNT");
    }
}
