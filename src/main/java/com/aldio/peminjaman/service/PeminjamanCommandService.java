package com.aldio.peminjaman.service;

import com.aldio.peminjaman.model.PeminjamanCommand;
import com.aldio.peminjaman.repository.PeminjamanCommandRepository;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeminjamanCommandService {

    private final PeminjamanCommandRepository peminjamanCommandRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "peminjaman-event";

    // Create
    public PeminjamanCommand createPeminjaman(PeminjamanCommand peminjaman) {
        peminjaman.setId(UUID.randomUUID().toString());
        peminjaman.setCreatedAt(LocalDateTime.now());
        PeminjamanCommand saved = peminjamanCommandRepository.save(peminjaman);

        saved.setEventType(PeminjamanCommand.EventType.CREATED);
        publishEventToKafka(saved);
        return saved;
    }

    // Update
    public PeminjamanCommand updatePeminjaman(String id, PeminjamanCommand peminjamanDetails) {
        PeminjamanCommand peminjaman = peminjamanCommandRepository.findById(id)
        .orElseThrow(()-> new RuntimeException(
            "peminjaman dengan id " + id + " tidak ditemukan"
        ));
        
        peminjaman.setTanggal_pinjam(peminjamanDetails.getTanggal_pinjam());
        peminjaman.setTanggal_kembali(peminjamanDetails.getTanggal_kembali());
        peminjaman.setBukuId(peminjamanDetails.getBukuId());
        peminjaman.setAnggotaId(peminjamanDetails.getAnggotaId());

        PeminjamanCommand updated = peminjamanCommandRepository.save(peminjaman);

        updated.setEventType(PeminjamanCommand.EventType.UPDATED);
        publishEventToKafka(updated);

        return updated;
    }

    // Delete
    public void deletePeminjaman(String id) {
        if (!peminjamanCommandRepository.existsById(id)) {
            throw new RuntimeException("Peminjaman dengan id " + id + " tidak ditemukan");
        }

        peminjamanCommandRepository.deleteById(id);
        log.info("Peminjaman dengan id {} berhasil dihapus!", id);

        PeminjamanCommand deleted = new PeminjamanCommand();
        deleted.setId(id);
        deleted.setEventType(PeminjamanCommand.EventType.DELETED);
        publishEventToKafka(deleted);
    }

    private void publishEventToKafka(PeminjamanCommand event){
        try {
            kafkaTemplate.send(TOPIC, event.getId(), event).whenComplete((result, ex)-> {
                if(ex == null){
                    log.info("Berhasil mengirim event {} dengan id {} ke partisi {} offset {}", 
                        event.getEventType(),
                        event.getId(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset()
                    );
                }else{
                    log.error("Gagal mempublish event {} dengan id {}", event.getEventType(), event.getId(), ex);
                }
            });
        } catch (Exception e) {
            log.error("Gagal mengirim event ke Kafka dengan id {}", event.getId(), e);
        }
    }
}


