package com.aldio.pengembalian.service;

import com.aldio.pengembalian.model.PengembalianCommand;
import com.aldio.pengembalian.repository.PengembalianCommandRepository;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PengembalianCommandService {

    private final PengembalianCommandRepository pengembalianRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    private static final String TOPIC = "pengembalian-events";

    // === Create ===
    public PengembalianCommand createPengembalian(PengembalianCommand pengembalian) {
        pengembalian.setId(UUID.randomUUID().toString());
        pengembalian.setCreatedAt(LocalDateTime.now());
        PengembalianCommand saved = pengembalianRepository.save(pengembalian);

        saved.setEventType(PengembalianCommand.EventType.CREATED);
        publishEventToKafka(saved);
        return saved;
    }   

    public PengembalianCommand updatePengembalian(String id, PengembalianCommand pengembalianDetail) {
        PengembalianCommand pengembalian = pengembalianRepository.findById(id).orElseThrow(()-> new RuntimeException("gagal mendapatkan id")); 
            pengembalian.setTanggal_dikembalikan(pengembalianDetail.getTanggal_dikembalikan());
            pengembalian.setTerlambat(pengembalianDetail.getTerlambat());
            pengembalian.setDenda(pengembalianDetail.getDenda());
            pengembalian.setPeminjamanId(pengembalianDetail.getPeminjamanId());

            PengembalianCommand updated = pengembalianRepository.save(pengembalian);

            updated.setEventType(PengembalianCommand.EventType.UPDATED);
            publishEventToKafka(updated);

            return updated;
    }

    public void deletePengembalian(String id) {
        pengembalianRepository.deleteById(id);

        PengembalianCommand deleted = new PengembalianCommand();
        deleted.setId(id);
        deleted.setEventType(PengembalianCommand.EventType.DELETED);
        publishEventToKafka(deleted);
    }

    private void publishEventToKafka(PengembalianCommand event){
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
