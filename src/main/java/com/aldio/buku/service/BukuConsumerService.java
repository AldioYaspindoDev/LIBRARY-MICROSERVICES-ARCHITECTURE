package com.aldio.buku.service;

import com.aldio.buku.model.Buku;
import com.aldio.buku.model.Buku.BukuStatus;
import com.aldio.buku.repository.BukuRepository;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BukuConsumerService {

    private final BukuRepository bukuRepository;
    private final EmailService emailService;

    public BukuConsumerService(BukuRepository bukuRepository, EmailService emailService) {
        this.bukuRepository = bukuRepository;
        this.emailService = emailService;
    }

    @RabbitListener(queues = "${app.rabbitmq.queue}")
    @Transactional
    public void receiveBuku(@Payload Buku buku) {
        try {
            System.out.println("Buku received from RabbitMQ: " + buku);

            // Update status buku jadi DIPINJAM
            buku.setStatus(BukuStatus.DIPINJAM);
            bukuRepository.save(buku);

            // Proses bisnis (misalnya delay, lalu kirim email)
            processBuku(buku);

            // Update status setelah selesai diproses
            buku.setStatus(BukuStatus.TERSEDIA);
            buku.setProcessedAt(java.time.LocalDateTime.now());
            bukuRepository.save(buku);

            System.out.println("Buku processed successfully: " + buku.getId());

        } catch (Exception e) {
            System.err.println("Error processing buku: " + buku.getId() + ", Error: " + e.getMessage());

            // Update status jika gagal
            buku.setStatus(BukuStatus.DIPINJAM); // atau bikin status FAILED kalau perlu
            bukuRepository.save(buku);

            // Bisa tambahkan retry / dead-letter queue
            throw new RuntimeException("Failed to process buku", e);
        }
    }

    // CODE UNTUK MEMPROSES DATA DAN MENGIRIM EMAIL
    @Async
    public void processBuku(Buku buku) {
        if (buku == null) {
            System.err.println("Buku is null, cannot process.");
            return;
        }

        System.out.println("Processing buku: " + (buku.getId() != null ? buku.getId() : "No ID"));

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (buku.getCustomerEmail() != null && buku.getId() != null) {
            emailService.sendBukuConfirmation(
                    buku.getCustomerEmail(),
                    buku.getId().toString(),
                    buku.getJudul());
            System.out.println("Buku processing completed: " + buku.getId());
        } else {
            System.err.println("Buku data incomplete, email not sent.");
        }
    }
}
