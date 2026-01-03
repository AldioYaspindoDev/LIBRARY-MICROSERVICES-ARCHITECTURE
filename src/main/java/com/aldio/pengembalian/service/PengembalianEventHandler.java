package com.aldio.pengembalian.service;

import com.aldio.pengembalian.model.PengembalianCommand;
import com.aldio.pengembalian.model.PengembalianQuery;
import com.aldio.pengembalian.repository.PengembalianQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PengembalianEventHandler {
    private final PengembalianQueryRepository peminjamanQueryRepository;

    @KafkaListener(topics = "pengembalian-events", groupId = "${spring.kafka.consumer.group-id}")
    public void consumer(PengembalianCommand event) {
        if(event == null || event.getId() == null || event.getEventType() == null){
            log.warn("Menerima Event yang tidak valid karena id atau eventype tidak ditemukan");
            return;
        }

        PengembalianCommand.EventType eventType = event.getEventType();
        String PinjamId = event.getId();

        switch(eventType) {
            case CREATED:
            case UPDATED:
                PengembalianQuery peminjamanDocument = new PengembalianQuery(event);
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
