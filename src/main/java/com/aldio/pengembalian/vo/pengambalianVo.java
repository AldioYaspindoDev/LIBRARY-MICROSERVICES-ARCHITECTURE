package com.aldio.pengembalian.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class pengambalianVo {
    private Peminjaman peminjaman;       // Data peminjaman
    private String tanggalDikembalikan;  // Input tanggal dikembalikan
    private long lamaPinjam;             // Lama pinjam (hari)
    private long terlambat;              // Hari keterlambatan
    private int denda;                   // Denda (1000 per hari terlambat)
}
