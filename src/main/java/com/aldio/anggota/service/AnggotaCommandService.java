package com.aldio.anggota.service;

import com.aldio.anggota.model.AnggotaCommand;
import com.aldio.anggota.repository.AnggotaCommandRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor // Anotasi ini sudah membuat constructor untuk final fields
@Slf4j
public class AnggotaCommandService {
    private final AnggotaCommandRepository anggotaRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "anggota-events"; 

    // logika crate data
    public AnggotaCommand saveAnggota(AnggotaCommand anggota){
        anggota.setId(UUID.randomUUID().toString());
        anggota.setCreatedAt(LocalDateTime.now());

        AnggotaCommand saved = anggotaRepository.save(anggota);
        saved.setEventType(AnggotaCommand.EventType.CREATED);
        publishEventToKafka(saved);
        return saved;
    }

    //logika mengedit data
    public AnggotaCommand updateAnggota(String id, AnggotaCommand anggotaDetails){
        AnggotaCommand existingAnggota = anggotaRepository.findById(id).orElse(null);
        if (existingAnggota != null) {
            existingAnggota.setNim(anggotaDetails.getNim());
            existingAnggota.setNama(anggotaDetails.getNama());
            existingAnggota.setAlamat(anggotaDetails.getAlamat());
            existingAnggota.setJenis_kelamin(anggotaDetails.getJenis_kelamin());
        }

        AnggotaCommand updated = anggotaRepository.save(existingAnggota);

        updated.setEventType(AnggotaCommand.EventType.UPDATED);
        publishEventToKafka(updated);
        return updated;
    }

    // logika menghapus data
    public void deleteAnggota(String id){
        anggotaRepository.deleteById(id);
        AnggotaCommand deleted = new AnggotaCommand();
        deleted.setId(id);
        deleted.setEventType(AnggotaCommand.EventType.DELETED);
        publishEventToKafka(deleted);
    }

        private void publishEventToKafka(AnggotaCommand event) {
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
                        }
                    });
        } catch (Exception e) {
            log.error("❌ Exception saat mengirim event ke Kafka untuk ID: {}", event.getId(), e);
        }
    }
}
