package com.aldio.pengembalian.vo;

import com.aldio.pengembalian.model.PengembalianCommand;

public class ResponseTemplate {
    private PengembalianCommand pengembalian;
    private Peminjaman peminjaman;

    public ResponseTemplate(){

    }

    public ResponseTemplate(PengembalianCommand pengembalian, Peminjaman peminjaman){
        this.pengembalian = pengembalian;
        this.peminjaman = peminjaman;
    }


    public PengembalianCommand getPengembalian() {
        return pengembalian;
    }

    public void setPengembalian(PengembalianCommand pengembalian) {
        this.pengembalian = pengembalian;
    }

    public Peminjaman getPeminjaman() {
        return peminjaman;
    }

    public void setPeminjaman(Peminjaman peminjaman) {
        this.peminjaman = peminjaman;
    }
}
