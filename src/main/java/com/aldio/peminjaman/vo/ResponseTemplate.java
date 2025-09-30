package com.aldio.peminjaman.vo;

import com.aldio.peminjaman.model.Peminjaman;

public class ResponseTemplate {
    private Peminjaman peminjaman;
    private Buku buku;
    private Anggota anggota;

    public ResponseTemplate() {
    }

    public ResponseTemplate(Peminjaman peminjaman, Buku buku, Anggota anggota) {
        this.peminjaman = peminjaman;
        this.buku = buku;
        this.anggota = anggota;
    }

    public Peminjaman getPeminjaman() {
        return peminjaman;
    }

    public void setPeminjaman(Peminjaman peminjaman) {
        this.peminjaman = peminjaman;
    }

    public Buku getBuku() {
        return buku;
    }

    public void setBuku(Buku buku) {
        this.buku = buku;
    }

    public Anggota getAnggota() {
        return anggota;
    }

    public void setAnggota(Anggota anggota) {
        this.anggota = anggota;
    }
}
