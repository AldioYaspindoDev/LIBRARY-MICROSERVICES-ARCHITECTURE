package com.aldio.pengembalian.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;

public class perhitunganDto {
    private Long peminjamanId;
    private String tanggalDikembalikan;
    private long lamaPinjam;
    private long terlambat;
    private int denda;   
}