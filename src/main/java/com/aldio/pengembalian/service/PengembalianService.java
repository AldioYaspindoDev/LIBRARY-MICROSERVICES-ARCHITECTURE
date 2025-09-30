package com.aldio.pengembalian.service;

import com.aldio.pengembalian.model.Pengembalian;
import com.aldio.pengembalian.repository.PengembalianRepository;
import com.aldio.pengembalian.vo.Peminjaman;
import com.aldio.pengembalian.vo.ResponseTemplate;
import com.aldio.pengembalian.vo.pengambalianVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class PengembalianService {

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private PengembalianRepository pengembalianRepository;

    @Autowired
    private RestTemplate restTemplate;

    // === CRUD dasar ===
    public Pengembalian savePengembalian(Pengembalian pengembalian) {
        return pengembalianRepository.save(pengembalian);
    }

    public List<Pengembalian> getAllPengembalian() {
        return pengembalianRepository.findAll();
    }

    public Optional<Pengembalian> getPengembalianById(Long id) {
        return pengembalianRepository.findById(id);
    }

    public Pengembalian updatePengembalian(Long id, Pengembalian pengembalianDetail) {
        return pengembalianRepository.findById(id).map(existing -> {
            existing.setTanggal_dikembalikan(pengembalianDetail.getTanggal_dikembalikan());
            existing.setTerlambat(pengembalianDetail.getTerlambat());
            existing.setDenda(pengembalianDetail.getDenda());
            existing.setPeminjamanId(pengembalianDetail.getPeminjamanId());
            return pengembalianRepository.save(existing);
        }).orElse(null);
    }

    public String deletePengembalian(Long id) {
        pengembalianRepository.deleteById(id);
        return "Pengembalian buku dengan id " + id + " berhasil dihapus";
    }

    // === Ambil Pengembalian + Peminjaman (via RestTemplate) ===
    public ResponseTemplate getPengembalianWithDetailsById(Long id) {
        Pengembalian pengembalian = pengembalianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pengembalian dengan id " + id + " tidak ditemukan"));

        List<ServiceInstance> peminjamanInstances = discoveryClient.getInstances("PEMINJAMAN-SERVICE");
        if (peminjamanInstances.isEmpty()) {
            throw new RuntimeException("Service PEMINJAMAN-SERVICE tidak ditemukan");
        }

        String peminjamanUrl = peminjamanInstances.get(0).getUri().toString() +
                "/api/peminjaman/" + pengembalian.getPeminjamanId();

        Peminjaman peminjaman = restTemplate.getForObject(peminjamanUrl, Peminjaman.class);

        ResponseTemplate vo = new ResponseTemplate();
        vo.setPeminjaman(peminjaman);
        vo.setPengembalian(pengembalian);

        return vo;
    }

    // === Hitung lama pinjam + denda (tidak simpan ke DB) ===
    public pengambalianVo hitungPengembalian(Peminjaman peminjaman, String tanggalDikembalikan) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate tglPinjam = LocalDate.parse(peminjaman.getTanggal_pinjam(), formatter);
        LocalDate tglKembali = LocalDate.parse(peminjaman.getTanggal_kembali(), formatter);
        LocalDate tglDikembalikan = LocalDate.parse(tanggalDikembalikan, formatter);

        long lamaPinjam = ChronoUnit.DAYS.between(tglPinjam, tglDikembalikan);

        long terlambat = 0;
        int denda = 0;
        if (tglDikembalikan.isAfter(tglKembali)) {
            terlambat = ChronoUnit.DAYS.between(tglKembali, tglDikembalikan);
            denda = (int) (terlambat * 1000);
        }

        return new pengambalianVo(
                peminjaman,
                tanggalDikembalikan,
                lamaPinjam,
                terlambat,
                denda
        );
    }
}
