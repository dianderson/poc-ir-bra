package br.com.bra.pocgradle.kafka.settings;

import lombok.Data;

@Data
public class KafkaTopicModel {
    private String name;
    private Integer ttlInDays;
    private Integer partitions;
    private Integer replicationFactory;
}