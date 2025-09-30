package com.aldio.peminjaman.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "peminjaman")
public class Peminjaman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String tanggal_pinjam;
    private String tanggal_kembali;
    private Long anggotaId;
    private Long bukuId;
}


