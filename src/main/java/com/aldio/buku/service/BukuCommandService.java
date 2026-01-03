package com.aldio.buku.service;

import com.aldio.buku.model.BukuCommand;
import com.aldio.buku.repository.BukuCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // Anotasi ini sudah membuat constructor untuk final fields
@Slf4j
public class BukuCommandService {

    private final BukuCommandRepository bukuCommandRepository; // Repository untuk PostgreSQL
    private final KafkaTemplate<String, Object> kafkaTemplate; // Gunakan Object agar lebih fleksibel

    private static final String TOPIC = "buku-events";

    public BukuCommand createBuku(BukuCommand buku) {
        // 1. Buat ID unik menggunakan UUID dan set ke objek buku
        buku.setId(UUID.randomUUID().toString());
        buku.setCreatedAt(LocalDateTime.now());

        // Simpan ke PostgreSQL (Command Database)
        BukuCommand saved = bukuCommandRepository.save(buku);

        // Kirim event ke Kafka dengan error handling
        saved.setEventType(BukuCommand.EventType.CREATED);
        publishEventToKafka(saved);

        return saved;
    }

    public BukuCommand updateBuku(String id, BukuCommand bukuDetails) {
        BukuCommand buku = bukuCommandRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Buku dengan id " + id + " tidak ditemukan"));

        buku.setJudul(bukuDetails.getJudul());
        buku.setPengarang(bukuDetails.getPengarang());
        buku.setPenerbit(bukuDetails.getPenerbit());
        buku.setTahunTerbit(bukuDetails.getTahunTerbit());
        buku.setCustomerEmail(bukuDetails.getCustomerEmail());
        buku.setStatus(bukuDetails.getStatus());
        buku.setProcessedAt(LocalDateTime.now());

        BukuCommand updated = bukuCommandRepository.save(buku);

        // Kirim event ke Kafka dengan error handling
        updated.setEventType(BukuCommand.EventType.UPDATED);
        publishEventToKafka(updated);

        return updated;
    }

    public void deleteBuku(String id) {
        if (!bukuCommandRepository.existsById(id)) {
            throw new RuntimeException("Buku dengan id " + id + " tidak ditemukan");
        }
        bukuCommandRepository.deleteById(id);
        log.info("Buku dengan ID {} berhasil dihapus dari PostgreSQL", id);

        // Buat event khusus untuk delete
        BukuCommand deleteE vent = new BukuCommand();
        deleteEvent.setId(id);
        deleteEvent.setEventType(BukuCommand.EventType.DELETED);

        // Kirim event delete ke Kafka
        publishEventToKafka(deleteEvent);
    }

    /**
     * Helper method untuk mengirim event ke Kafka dengan error handling.
     * Menggunakan message key (ID) untuk menjaga ordering per entity.
     */
    private void publishEventToKafka(BukuCommand event) {
        try {
            // Menggunakan ID sebagai key untuk menjaga ordering per entity
            kafkaTemplate.send(TOPIC, event.getId(), event)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            log.info("✅ Published {} event to Kafka for ID: {} | Partition: {} | Offset: {}",
                                    event.getEventType(),
                                    event.getId(),
                                    result.getRecordMetadata().partition(),
                                    result.getRecordMetadata().offset());
                        } else {
                            log.error("❌ Failed to publish {} event to Kafka for ID: {}",
                                    event.getEventType(), event.getId(), ex);
                            // TODO: Implement retry mechanism atau simpan ke dead letter queue
                        }
                    });
        } catch (Exception e) {
            log.error("❌ Exception saat mengirim event ke Kafka untuk ID: {}", event.getId(), e);
            // TODO: Implement fallback mechanism (misalnya Outbox Pattern)
        }
    }
}