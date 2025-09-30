package com.aldio.peminjaman.service;

import com.aldio.peminjaman.model.Peminjaman;
import com.aldio.peminjaman.repository.PeminjamanRepository;
import com.aldio.peminjaman.vo.Anggota;
import com.aldio.peminjaman.vo.Buku;
import com.aldio.peminjaman.vo.ResponseTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Optional;

@Service
public class PeminjamanService {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private PeminjamanRepository peminjamanRepository;

    @Autowired
    private RestTemplate restTemplate;

    // Create
    public Peminjaman savePeminjaman(Peminjaman peminjaman) {
        return peminjamanRepository.save(peminjaman);
    }

    // Read all
    public List<Peminjaman> getAllPeminjamans() {
        return peminjamanRepository.findAll();
    }

    // Read by ID
    public Optional<Peminjaman> getPeminjamanById(Long id) {
        return peminjamanRepository.findById(id);
    }

    // Update
    public Peminjaman updatePeminjaman(Long id, Peminjaman peminjamanDetails) {
        return peminjamanRepository.findById(id).map(existingPeminjaman -> {
            existingPeminjaman.setBukuId(peminjamanDetails.getBukuId());
            existingPeminjaman.setAnggotaId(peminjamanDetails.getAnggotaId());
            existingPeminjaman.setTanggal_pinjam(peminjamanDetails.getTanggal_pinjam());
            existingPeminjaman.setTanggal_kembali(peminjamanDetails.getTanggal_kembali());
            return peminjamanRepository.save(existingPeminjaman);
        }).orElse(null);
    }

    // Delete
    public String deletePeminjaman(Long id) {
        if (!peminjamanRepository.existsById(id)) {
            throw new RuntimeException("Peminjaman dengan id " + id + " tidak ditemukan");
        }
        peminjamanRepository.deleteById(id);
        return "Peminjaman dengan id " + id + " berhasil dihapus!";
    }

    // Get Peminjaman with Buku & Anggota
    public ResponseTemplate getPeminjamanWithDetailsById(Long id) {
        Peminjaman peminjaman = peminjamanRepository.findById(id).orElseThrow(() -> new RuntimeException("Peminjaman dengan id " + id + " tidak ditemukan"));

        // Ambil service BUKU
        List<ServiceInstance> bukuInstances = discoveryClient.getInstances("BUKU-SERVICE");
        if (bukuInstances.isEmpty()) {
            throw new RuntimeException("Service BUKU tidak ditemukan");
        }
        String bukuUrl = bukuInstances.get(0).getUri().toString() + "/api/buku/" + peminjaman.getBukuId();
        Buku buku = restTemplate.getForObject(bukuUrl, Buku.class);

        // Ambil service ANGGOTA
        List<ServiceInstance> anggotaInstances = discoveryClient.getInstances("ANGGOTA-SERVICE");
        if (anggotaInstances.isEmpty()) {
            throw new RuntimeException("Service ANGGOTA tidak ditemukan");
        }
        String anggotaUrl = anggotaInstances.get(0).getUri().toString() + "/api/anggota/" + peminjaman.getAnggotaId();
        Anggota anggota = restTemplate.getForObject(anggotaUrl, Anggota.class);

        // Build response
        ResponseTemplate vo = new ResponseTemplate();
        vo.setPeminjaman(peminjaman);
        vo.setBuku(buku);
        vo.setAnggota(anggota);

        return vo;
    }
}
