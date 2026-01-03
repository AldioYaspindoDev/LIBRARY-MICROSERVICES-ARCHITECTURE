package com.aldio.peminjaman.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "peminjaman") 
public class PeminjamanCommand implements Serializable{
    
    // Enum untuk tipe event CQRS
    public enum EventType {
        CREATED, UPDATED, DELETED
    }

    @Id
    @Column(name = "id", length = 36, nullable = false)
    private String id;

    @Column(name = "tanggal_pinjam")
    private String tanggal_pinjam;
    
    @Column(name = "tanggal_kembali")
    private String tanggal_kembali;
    
    private String anggotaId;
    
    private String bukuId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Transient // Tidak disimpan ke database, hanya untuk event Kafka
    private EventType eventType;
}