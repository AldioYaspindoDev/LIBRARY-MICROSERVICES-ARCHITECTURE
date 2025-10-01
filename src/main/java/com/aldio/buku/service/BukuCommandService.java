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
        buku.setId(UUID.randomUUID().toString()); // <-- TAMBAHKAN BARIS INI

        buku.setCreatedAt(LocalDateTime.now());

        // SEKARANG AMAN, karena buku.getId() sudah memiliki nilai
        BukuCommand saved = bukuCommandRepository.save(buku);

        // ... sisa kode
        saved.setEventType(BukuCommand.EventType.CREATED);
        kafkaTemplate.send(TOPIC, saved);
        log.info("Published CREATED event to Kafka for ID: {}", saved.getId());

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

        // Set event type sebelum dikirim ke Kafka
        updated.setEventType(BukuCommand.EventType.UPDATED);
        kafkaTemplate.send(TOPIC, updated);
        log.info("Published UPDATED event to Kafka for ID: {}", updated.getId());

        return updated;
    }

    public void deleteBuku(String id) {
        if (!bukuCommandRepository.existsById(id)) {
            throw new RuntimeException("Buku dengan id " + id + " tidak ditemukan");
        }
        bukuCommandRepository.deleteById(id);
        log.info("Buku dengan ID {} berhasil dihapus dari PostgreSQL", id);

        // Buat event khusus untuk delete
        BukuCommand deleteEvent = new BukuCommand();
        deleteEvent.setId(id);
        deleteEvent.setEventType(BukuCommand.EventType.DELETED);

        // Kirim event delete ke Kafka
        kafkaTemplate.send(TOPIC, deleteEvent);
        log.info("Published DELETED event to Kafka for ID: {}", id);
    }
}