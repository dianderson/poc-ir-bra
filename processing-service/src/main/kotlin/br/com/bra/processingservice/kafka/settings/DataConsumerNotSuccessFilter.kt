package br.com.bra.processingservice.kafka.settings

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.listener.adapter.RecordFilterStrategy


@Configuration
class DataConsumerNotSuccessFilter {
    @Bean
    fun notSuccessFilter(): RecordFilterStrategy<String?, String?> = RecordFilterStrategy { rec ->
        String(rec.headers().lastHeader("Status").value()).contains("SUCCESS")
    }
}