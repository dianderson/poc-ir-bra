package br.com.bra.pocgradle.kafka.my_account;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;

@Configuration
public class MyAccountConsumerFilter {
    @Bean
    RecordFilterStrategy<String, String> MyAccountFilter() {
        return rec -> !new String(rec.headers().lastHeader("products").value()).contains("MY_ACCOUNT");
    }
}
