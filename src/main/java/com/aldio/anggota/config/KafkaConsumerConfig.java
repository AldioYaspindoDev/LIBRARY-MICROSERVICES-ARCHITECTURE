package com.aldio.anggota.config;

import com.aldio.anggota.model.AnggotaCommand;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConsumerFactory<String, AnggotaCommand> consumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        // === Konfigurasi Kunci Untuk Deserialisasi JSON ===
        // 1. Tentukan class tujuan deserialisasi
        JsonDeserializer<AnggotaCommand> deserializer = new JsonDeserializer<>(AnggotaCommand.class);
        // 2. Izinkan deserialisasi dari package mana pun (untuk development)
        //    atau spesifikasikan package model Anda: deserializer.addTrustedPackages("com.aldio.buku.model");
        deserializer.addTrustedPackages("*");

        return new DefaultKafkaConsumerFactory<>(
                props,
                new StringDeserializer(), // Deserializer untuk 'key' pesan
                deserializer              // Deserializer untuk 'value' pesan (JSON)
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AnggotaCommand> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, AnggotaCommand> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
