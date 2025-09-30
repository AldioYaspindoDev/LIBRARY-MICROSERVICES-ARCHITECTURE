package com.aldio.buku.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "buku")
public class Buku {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BukuStatus status = BukuStatus.TERSEDIA;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    private LocalDateTime processedAt;

    public enum BukuStatus {
        DIPINJAM, TERSEDIA
    }

    // Constructor tambahan tanpa id dan tanggal
    public Buku(String judul, String pengarang, String penerbit, String tahunTerbit, String customerEmail) {
        this.judul = judul;
        this.pengarang = pengarang;
        this.penerbit = penerbit;
        this.tahunTerbit = tahunTerbit;
        this.customerEmail = customerEmail;
        this.status = BukuStatus.DIPINJAM;
        this.createdAt = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", judul buku='" + judul + '\'' +
                ", pengarang buku=" + pengarang +
                ", penerbit=" + penerbit +
                ", tahun terbit=" + tahunTerbit +
                ", customerEmail='" + customerEmail + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                '}';
    }
}
