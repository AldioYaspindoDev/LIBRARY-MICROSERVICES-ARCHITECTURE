package com.aldio.buku.service; // Atau com.aldio.buku.consumer

import com.aldio.buku.model.BukuCommand;
import com.aldio.buku.model.BukuQuery;
import com.aldio.buku.repository.BukuQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BukuEventHandler {

    // PERBAIKAN: Nama variabel disesuaikan agar lebih jelas
    private final BukuQueryRepository bukuQueryRepository;

    // PERBAIKAN: Anotasi @Transactional dihapus karena tidak relevan untuk logika ini di MongoDB
    @KafkaListener(topics = "buku-events", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(BukuCommand event) {
        if (event == null || event.getId() == null || event.getEventType() == null) {
            log.warn("Menerima event tidak valid (null, tanpa ID, atau tanpa tipe event), pesan diabaikan.");
            return;
        }

        BukuCommand.EventType eventType = event.getEventType();
        String bookId = event.getId();

        // Logika untuk menangani setiap jenis event
        switch (eventType) {
            case CREATED:
            case UPDATED:
                // PERBAIKAN: Logika yang jauh lebih bersih.
                // 1. Langsung konversi event BukuCommand menjadi dokumen BukuQuery.
                BukuQuery bukuDocument = new BukuQuery(event);

                // 2. Simpan ke MongoDB. .save() akan melakukan INSERT jika ID belum ada,
                //    atau UPDATE jika ID sudah ada (operasi "upsert").
                bukuQueryRepository.save(bukuDocument);

                // PERBAIKAN: Pesan log disesuaikan dengan aksi dan target database yang benar.
                log.info("✅ Data buku berhasil disimpan/diperbarui di MONGODB untuk ID: {}", bookId);
                break;

            case DELETED:
                bukuQueryRepository.deleteById(bookId);
                // PERBAIKAN: Pesan log disesuaikan.
                log.info("🗑️ Data buku berhasil dihapus dari MONGODB untuk ID: {}", bookId);
                break;

            default:
                log.warn("Tipe event tidak dikenali: {}. Pesan diabaikan.", eventType);
                break;
        }
    }
}