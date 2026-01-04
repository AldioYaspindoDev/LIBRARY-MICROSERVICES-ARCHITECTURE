package com.aldio.anggota.service;

import com.aldio.anggota.model.AnggotaCommand;
import com.aldio.anggota.model.AnggotaQuery;
import com.aldio.anggota.repository.AnggotaQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BukuEventHandler {

    // PERBAIKAN: Nama variabel disesuaikan agar lebih jelas
    private final AnggotaQueryRepository bukuQueryRepository;

    // PERBAIKAN: Anotasi @Transactional dihapus karena tidak relevan untuk logika ini di MongoDB
    @KafkaListener(topics = "anggota-events", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(AnggotaCommand event) {
        if (event == null || event.getId() == null || event.getEventType() == null) {
            log.warn("Menerima event tidak valid (null, tanpa ID, atau tanpa tipe event), pesan diabaikan.");
            return;
        }

        AnggotaCommand.EventType eventType = event.getEventType();
        String anggotaId = event.getId();

        // Logika untuk menangani setiap jenis event
        switch (eventType) {
            case CREATED:
            case UPDATED:
                AnggotaQuery bukuDocument = new AnggotaQuery(event);
                bukuQueryRepository.save(bukuDocument);
                log.info("✅ Data buku berhasil disimpan/diperbarui di MONGODB untuk ID: {}", anggotaId);
                break;
            case DELETED:
                bukuQueryRepository.deleteById(anggotaId);
                log.info("🗑️ Data buku berhasil dihapus dari MONGODB untuk ID: {}", anggotaId);
                break;
            default:
                log.warn("Tipe event tidak dikenali: {}. Pesan diabaikan.", eventType);
                break;
        }
    }
}
