package com.aldio.buku.service;

import com.aldio.buku.model.BukuQuery;
import com.aldio.buku.repository.BukuQueryRepository; // PERBAIKAN: Gunakan repository untuk MongoDB
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor // Lebih baik gunakan ini daripada constructor manual
public class BukuQueryService {

    // PERBAIKAN: Gunakan repository yang terhubung ke MongoDB
    private final BukuQueryRepository bukuRepository;

    public List<BukuQuery> getAllBukus() {
        return bukuRepository.findAll();
    }

    public BukuQuery getBukuById(String id) {
        return bukuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Buku tidak ditemukan dengan id: " + id));
    }
}