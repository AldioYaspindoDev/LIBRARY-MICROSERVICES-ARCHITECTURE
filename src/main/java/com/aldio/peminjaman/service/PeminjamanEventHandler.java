package com.aldio.peminjaman.service;
import com.aldio.peminjaman.model.PeminjamanCommand;
import com.aldio.peminjaman.model.PeminjamanQuery;
import com.aldio.peminjaman.repository.PeminjamanQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeminjamanEventHandler {
    private final PeminjamanQueryRepository peminjamanQueryRepository;

    @KafkaListener(topics = "peminjaman-event", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(PeminjamanCommand event) {
        if(event == null || event.getId() == null || event.getEventType() == null){
            log.warn("Menerima Event yang tidak valid karena id atau eventype tidak ditemukan");
            return;
        }

        PeminjamanCommand.EventType eventType = event.getEventType();
        String PinjamId = event.getId();

        switch(eventType) {
            case CREATED:
            case UPDATED:
                PeminjamanQuery peminjamanDocument = new PeminjamanQuery(event);
                peminjamanQueryRepository.save(peminjamanDocument);
                log.info("data peminjaman berhasil disimpan di mongoDB dengan id ", PinjamId);
                break;
            case DELETED:
                peminjamanQueryRepository.deleteById(PinjamId);
                log.info("data peminjaman berhasil disimpan di mongoDB dengan id", PinjamId);
                break;
            default:
                log.warn("event tidak dikenali", eventType);
                break;    
        }
    }
}
