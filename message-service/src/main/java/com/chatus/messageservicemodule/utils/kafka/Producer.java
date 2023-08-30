package com.chatus.messageservicemodule.utils.kafka;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;


@Service
public class Producer {

    @Value("${general.topic.kafka}")
    private String TOPIC;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

//    public void sendMessage(String message, Long created) {
//        kafkaTemplate.send(TOPIC, null, created, null, message);
//    }

    public void sendMessage(String message) {
        kafkaTemplate.send(TOPIC, message);
    }
}
