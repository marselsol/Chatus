package com.chatus.messageservicemodule.utils.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Properties;

@Slf4j
@Component
public class ConsumerFactory {

    @Value("${spring.kafka.bootstrap-servers}")
    private String kafkaBrokerUrl;

    public KafkaConsumer createConsumer(String chatName) {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaBrokerUrl);
        props.put("group.id", chatName);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        TopicPartition specificPartition = new TopicPartition(chatName, 0);
        try {
            consumer.assign(Collections.singletonList(specificPartition));
//        consumer.subscribe(Collections.singletonList(chatName));
        } catch (Exception e) {
            log.error("Error assigning partition to consumer: {}", e.getMessage(), e);
        }
        return consumer;
    }


}
