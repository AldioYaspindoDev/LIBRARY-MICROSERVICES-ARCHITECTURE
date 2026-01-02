package com.aldio.peminjaman.vo;

import com.aldio.peminjaman.model.PeminjamanQuery;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTemplate {
    private PeminjamanQuery peminjaman;
    private Buku buku;
    private Anggota anggota;
}
