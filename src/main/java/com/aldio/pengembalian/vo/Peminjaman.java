package com.aldio.pengembalian.vo;



public class Peminjaman {
    private Long id;
    private String tanggal_pinjam;
    private String tanggal_kembali;
    private String anggotaId;
    private String bukuId;

    public Peminjaman(){

    }

    public Peminjaman(Long id, String tanggal_pinjam, String tanggal_kembali, String anggotaId, String bukuId){
        this.id = id;
        this.tanggal_pinjam = tanggal_pinjam;
        this.tanggal_kembali = tanggal_kembali;
        this.anggotaId = anggotaId;
        this.bukuId = bukuId;
    }

    public Long getId() {
        return id;
    }

    public String getTanggal_pinjam() {
        return tanggal_pinjam;
    }

    public String getTanggal_kembali() {
        return tanggal_kembali;
    }

    public String getAnggotaId() {
        return anggotaId;
    }

    public String getBukuId() {
        return bukuId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTanggal_pinjam(String tanggal_pinjam) {
        this.tanggal_pinjam = tanggal_pinjam;
    }

    public void setTanggal_kembali(String tanggal_kembali) {
        this.tanggal_kembali = tanggal_kembali;
    }

    public void setAnggotaId(String anggotaId) {
        this.anggotaId = anggotaId;
    }

    public void setBukuId(String bukuId) {
        this.bukuId = bukuId;
    }    
}
