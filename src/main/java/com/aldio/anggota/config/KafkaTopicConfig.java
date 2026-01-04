package com.aldio.anggota.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic bukuTopic() {
        return new NewTopic("anggota-events", 1, (short) 1);
    }
}

