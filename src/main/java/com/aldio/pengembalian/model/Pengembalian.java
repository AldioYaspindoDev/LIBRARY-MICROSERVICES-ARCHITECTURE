package com.aldio.pengembalian.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Pengembalian")
public class Pengembalian {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String tanggal_dikembalikan;
    private String terlambat;
    private String denda;

    private Long peminjamanId;
}
