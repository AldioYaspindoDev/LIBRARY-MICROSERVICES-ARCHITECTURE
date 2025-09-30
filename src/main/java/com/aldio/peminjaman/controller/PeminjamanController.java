package com.aldio.peminjaman.controller;

import com.aldio.peminjaman.model.Peminjaman;
import com.aldio.peminjaman.service.PeminjamanService;
import com.aldio.peminjaman.vo.ResponseTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/peminjaman")
public class PeminjamanController {

    @Autowired
    private PeminjamanService peminjamanService;

    // Get Peminjaman + Buku + Anggota
    @GetMapping("/{id}/details")
    public ResponseTemplate getPeminjamanWithDetails(@PathVariable Long id) {
        return peminjamanService.getPeminjamanWithDetailsById(id);
    }

    // Create
    @PostMapping
    public Peminjaman createPeminjaman(@RequestBody Peminjaman peminjaman) {
        return peminjamanService.savePeminjaman(peminjaman);
    }

    // Read all
    @GetMapping
    public List<Peminjaman> getAllPeminjaman() {
        return peminjamanService.getAllPeminjamans();
    }

    // Read by ID
    @GetMapping("/{id}")
    public Peminjaman getPeminjamanById(@PathVariable Long id) {
        return peminjamanService.getPeminjamanById(id).orElse(null);
    }

    // Update
    @PutMapping("/{id}")
    public Peminjaman updatePeminjaman(@PathVariable Long id, @RequestBody Peminjaman peminjamanDetails) {
        return peminjamanService.updatePeminjaman(id, peminjamanDetails);
    }

    // Delete
    @DeleteMapping("/{id}")
    public String deletePeminjaman(@PathVariable Long id) {
        return peminjamanService.deletePeminjaman(id);
    }
}
