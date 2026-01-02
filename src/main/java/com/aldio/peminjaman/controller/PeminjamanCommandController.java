package com.aldio.peminjaman.controller;

import com.aldio.peminjaman.model.PeminjamanCommand;
import com.aldio.peminjaman.service.PeminjamanCommandService;
// import com.aldio.peminjaman.vo.ResponseTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/peminjaman/command")
public class PeminjamanCommandController {

    @Autowired
    private PeminjamanCommandService peminjamanService;

    // Create
    @PostMapping
    public ResponseEntity<PeminjamanCommand> createPeminjaman(@Valid @RequestBody PeminjamanCommand peminjaman) {
        return ResponseEntity.status(201).body(peminjamanService.createPeminjaman(peminjaman));
    }

    // Update
    @PutMapping("/{id}")
    public ResponseEntity<PeminjamanCommand> updatePeminjaman(@PathVariable String id, @RequestBody PeminjamanCommand peminjamanDetails) {
        return ResponseEntity.ok(peminjamanService.updatePeminjaman(id, peminjamanDetails));
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePeminjaman(@PathVariable String id) {
        peminjamanService.deletePeminjaman(id);
        return ResponseEntity.ok("buku berhasil dihapus");
    }
}
