package com.aldio.peminjaman.service;
import com.aldio.peminjaman.repository.PeminjamanQueryRepository;
import com.aldio.peminjaman.model.PeminjamanQuery;
import com.aldio.peminjaman.vo.Anggota;
import com.aldio.peminjaman.vo.Buku;
import com.aldio.peminjaman.vo.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PeminjamanQueryService {
    private final PeminjamanQueryRepository peminjamanRepository;
    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public List<PeminjamanQuery> getAllPeminjaman(){
        return peminjamanRepository.findAll();
    }

    public PeminjamanQuery getPeminjamanById(String id){
        return peminjamanRepository.findById(id).orElseThrow(()-> new RuntimeException(
            "peminjaman dengan id " + id + " tidak ditemukan"
        ));
    }

    // Get Peminjaman with Buku & Anggota details
    public ResponseTemplate getPeminjamanWithDetailsById(String id) {
        PeminjamanQuery peminjaman = peminjamanRepository.findById(id)
            .orElseThrow(() -> new RuntimeException(
                "Peminjaman dengan id " + id + " tidak ditemukan"));

        // Ambil service BUKU
        List<ServiceInstance> bukuInstances = discoveryClient.getInstances("BUKU-SERVICE");
        if (bukuInstances.isEmpty()) {
            throw new RuntimeException("Service BUKU tidak ditemukan");
        }
        String bukuUrl = bukuInstances.get(0).getUri().toString() + "/api/buku/query/" + peminjaman.getBukuId();
        Buku buku = restTemplate.getForObject(bukuUrl, Buku.class);

        // Ambil service ANGGOTA
        List<ServiceInstance> anggotaInstances = discoveryClient.getInstances("ANGGOTA-SERVICE");
        if (anggotaInstances.isEmpty()) {
            throw new RuntimeException("Service ANGGOTA tidak ditemukan");
        }
        String anggotaUrl = anggotaInstances.get(0).getUri().toString() + "/api/anggota/query/" + peminjaman.getAnggotaId();
        Anggota anggota = restTemplate.getForObject(anggotaUrl, Anggota.class);

        // Build response
        ResponseTemplate response = new ResponseTemplate();
        response.setPeminjaman(peminjaman);
        response.setBuku(buku);
        response.setAnggota(anggota);

        log.info("Berhasil mengambil data peminjaman dengan detail buku dan anggota untuk id: {}", id);
        return response;
    }
}
