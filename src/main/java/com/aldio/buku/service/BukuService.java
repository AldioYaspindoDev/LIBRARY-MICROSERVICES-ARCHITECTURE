package com.aldio.buku.service;

import com.aldio.buku.model.Buku;
import com.aldio.buku.repository.BukuRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BukuService {
    @Autowired
    private BukuRepository bukuRepository;

    public Buku saveBuku(Buku buku) {
        try {
            return bukuRepository.save(buku);
        } catch (Exception e) {
            throw new RuntimeException("Error saving book: " + e.getMessage(), e);
        }
    }

    public List<Buku> getAllBukus() {
        try {
            return bukuRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error fetching all books: " + e.getMessage(), e);
        }
    }

    public Buku getBukuById(Long id) {
        try {
            Optional<Buku> buku = bukuRepository.findById(id);
            return buku.orElse(null);
        } catch (Exception e) {
            throw new RuntimeException("Error fetching book by id: " + e.getMessage(), e);
        }
    }

    public Buku updateBuku(Long id, Buku bukuDetails) {
        try {
            Optional<Buku> existingBukuOpt = bukuRepository.findById(id);
            if (existingBukuOpt.isPresent()) {
                Buku existingBuku = existingBukuOpt.get();
                existingBuku.setJudul(bukuDetails.getJudul());
                existingBuku.setPengarang(bukuDetails.getPengarang());
                existingBuku.setPenerbit(bukuDetails.getPenerbit());
                existingBuku.setTahunTerbit(bukuDetails.getTahunTerbit());
                return bukuRepository.save(existingBuku);
            }
            return null;
        } catch (Exception e) {
            throw new RuntimeException("Error updating book: " + e.getMessage(), e);
        }
    }

    public String deleteBuku(Long id) {
        try {
            if (bukuRepository.existsById(id)) {
                bukuRepository.deleteById(id);
                return "Buku dengan id " + id + " berhasil dihapus";
            } else {
                return "Buku dengan id " + id + " tidak ditemukan";
            }
        } catch (Exception e) {
            throw new RuntimeException("Error deleting book: " + e.getMessage(), e);
        }
    }
}