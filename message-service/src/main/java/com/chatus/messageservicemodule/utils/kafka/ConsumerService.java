package com.chatus.messageservicemodule.utils.kafka;

import com.chatus.messageservicemodule.entity.Message;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndTimestamp;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.TopicPartition;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class ConsumerService {

    private final ConsumerFactory consumerFactory;
    private final ObjectMapper objectMapper;

    public ConsumerService(ConsumerFactory consumerFactory, ObjectMapper objectMapper) {
        this.consumerFactory = consumerFactory;
        this.objectMapper = objectMapper;
    }

    public List<Message> getMessagesFromTopicFromDate(String topicName, LocalDateTime dateTime) {
        long targetTimestamp = dateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
        List<Message> messagesFromDate = new ArrayList<>();
        KafkaConsumer<String, String> consumer = consumerFactory.createConsumer(topicName);
        Map<TopicPartition, Long> timestamps = new HashMap<>();
        TopicPartition topicPartition = new TopicPartition(topicName, 0);
        timestamps.put(new TopicPartition(topicName, 0), targetTimestamp);
        try {
            Map<TopicPartition, OffsetAndTimestamp> offsets = consumer.offsetsForTimes(timestamps);
            OffsetAndTimestamp offsetAndTimestamp = offsets.get(topicPartition);
            if (offsetAndTimestamp != null) {
                long offset = offsetAndTimestamp.offset();
                consumer.seek(topicPartition, offset);
            } else {
                throw new KafkaException("OffsetAndTimestamp is null. Possibly a problem with the consumer.");
            }
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                String messageStringJSON = record.value();
                Message message;
                try {
                    message = objectMapper.readValue(messageStringJSON, Message.class);
                    messagesFromDate.add(message);
                } catch (JsonProcessingException e) {
                    log.error("Error processing JSON: {}", e.getMessage(), e);
                }
            }
        } catch (Exception e) {
            log.error("An error occurred while reading messages by time: {}", e.getMessage(), e);
        } finally {
//            consumer.close();
        }
        return messagesFromDate;
    }
}






