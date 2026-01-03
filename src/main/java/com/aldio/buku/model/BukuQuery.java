package com.aldio.buku.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "buku") // PERBAIKAN: Anotasi untuk MongoDB
public class BukuQuery implements Serializable {
    @Id // Menggunakan org.springframework.data.annotation.Id
    private String id;

    private String judul;
    private String pengarang;
    private String penerbit;
    private String tahunTerbit;
    private String customerEmail;
    private BukuStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;

    /**
     * Constructor ini sangat berguna untuk Kafka Listener.
     * Saat menerima event BukuCommand, kita bisa langsung membuat
     * objek BukuQuery dari event tersebut untuk disimpan ke MongoDB.
     */
    public BukuQuery(BukuCommand command) {
        this.id = command.getId();
        this.judul = command.getJudul();
        this.pengarang = command.getPengarang();
        this.penerbit = command.getPenerbit();
        this.tahunTerbit = command.getTahunTerbit();
        this.customerEmail = command.getCustomerEmail();
        this.status = command.getStatus();
        this.createdAt = command.getCreatedAt();
        this.processedAt = command.getProcessedAt();
    }

    @Override
    public String toString() {
        // PERBAIKAN: Menwaery"
        return "BukuQuery{" +
                "id=" + id +
                ", judul buku='" + judul + '\'' +
                ", pengarang buku=" + pengarang +
                ", penerbit=" + penerbit +
                ", tahun terbit=" + tahunTerbit +
                ", customerEmail='" + customerEmail + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", processedAt=" + processedAt +
                '}';
    }
}