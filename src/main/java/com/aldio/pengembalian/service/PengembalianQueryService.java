package com.aldio.pengembalian.service;

import java.util.List;
import java.util.Optional;

import com.aldio.pengembalian.model.PengembalianQuery;
import com.aldio.pengembalian.repository.PengembalianQueryRepository;
import com.aldio.pengembalian.vo.Peminjaman;
import com.aldio.pengembalian.vo.ResponseTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Slf4j
public class PengembalianQueryService {

    private final PengembalianQueryRepository pengembalianRepository;
    private final DiscoveryClient discoveryClient;
    private final RestTemplate restTemplate;

    public List<PengembalianQuery> getAllPengembalian() {
        return pengembalianRepository.findAll();
    }

    public Optional<PengembalianQuery> getPengembalianById(String id) {
        return pengembalianRepository.findById(id);
    }    

        // === Ambil Pengembalian + Peminjaman (via RestTemplate) ===
    public ResponseTemplate getPengembalianWithDetailsById(String id) {
        PengembalianQuery pengembalian = pengembalianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pengembalian dengan id " + id + " tidak ditemukan"));

        List<ServiceInstance> peminjamanInstances = discoveryClient.getInstances("PEMINJAMAN-SERVICE");
        if (peminjamanInstances.isEmpty()) {
            throw new RuntimeException("Service PEMINJAMAN-SERVICE tidak ditemukan");
        }

        String peminjamanUrl = peminjamanInstances.get(0).getUri().toString() +
                "/api/peminjaman/query" + pengembalian.getPeminjamanId();

        Peminjaman peminjaman = restTemplate.getForObject(peminjamanUrl, Peminjaman.class);

        ResponseTemplate response = new ResponseTemplate();
        response.setPeminjaman(peminjaman);
        return response;
    }

}
