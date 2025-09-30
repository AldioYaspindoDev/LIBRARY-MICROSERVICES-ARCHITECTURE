package com.aldio.anggota.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "anggotas")
public class Anggota{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    private String nim;
    private String nama;
    private String alamat;
    private String jenis_kelamin;
}