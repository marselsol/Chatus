package com.chatus.messageservicemodule.utils.kafka;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
@EnableKafka
public class KafkaConfig {

    @Value("${general.topic.kafka}")
    private String nameGeneralTopicKafka;

    @Bean
    public NewTopic createIncomingMessagesTopic() {
        return TopicBuilder.name(nameGeneralTopicKafka)
                .partitions(1)
                .replicas(1)
                .build();
    }
}
