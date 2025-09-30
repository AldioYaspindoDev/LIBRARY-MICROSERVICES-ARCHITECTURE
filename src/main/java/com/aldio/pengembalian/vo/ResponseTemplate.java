package com.aldio.pengembalian.vo;

import com.aldio.pengembalian.model.Pengembalian;

public class ResponseTemplate {
    private Pengembalian pengembalian;
    private Peminjaman peminjaman;

    public ResponseTemplate(){

    }

    public ResponseTemplate(Pengembalian pengembalian, Peminjaman peminjaman){
        this.pengembalian = pengembalian;
        this.peminjaman = peminjaman;
    }


    public Pengembalian getPengembalian() {
        return pengembalian;
    }

    public void setPengembalian(Pengembalian pengembalian) {
        this.pengembalian = pengembalian;
    }

    public Peminjaman getPeminjaman() {
        return peminjaman;
    }

    public void setPeminjaman(Peminjaman peminjaman) {
        this.peminjaman = peminjaman;
    }
}
