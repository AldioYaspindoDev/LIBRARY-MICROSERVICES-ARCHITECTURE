package com.aldio.buku.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity // PERBAIKAN: Anotasi untuk JPA (PostgreSQL)
@Table(name = "buku") // PERBAIKAN: Menentukan nama tabel di PostgreSQL
public class BukuCommand implements Serializable {

    @Id // Menggunakan jakarta.persistence.Id
    private String id;

    @NotBlank(message = "Judul buku wajib diisi")
    @Column(nullable = false)
    private String judul;

    @NotBlank(message = "Pengarang wajib diisi")
    @Column(nullable = false)
    private String pengarang;

    @NotBlank(message = "Penerbit wajib diisi")
    @Column(nullable = false)
    private String penerbit;

    @NotBlank(message = "Tahun terbit wajib diisi")
    @Column(name = "tahun_terbit", nullable = false)
    private String tahunTerbit;

    @Email(message = "Email tidak valid")
    @NotBlank(message = "Email customer wajib diisi")
    @Column(nullable = false)
    private String customerEmail;

    @Enumerated(EnumType.STRING) // Menyimpan enum sebagai String di database
    @Column(nullable = false)
    private BukuStatus status = BukuStatus.TERSEDIA;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime processedAt;

    // Field ini hanya untuk proses pengiriman event ke Kafka, tidak disimpan di DB
    @Transient // PERBAIKAN: Anotasi agar field in+i tidak dipetakan ke kolom database
    private EventType eventType;

    public enum EventType {
        CREATED,
        UPDATED,
        DELETED
    }
}