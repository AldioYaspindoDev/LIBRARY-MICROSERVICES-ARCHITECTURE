package com.aldio.buku.controller;

import com.aldio.buku.model.BukuCommand;
import com.aldio.buku.service.BukuCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/buku/command")
@RequiredArgsConstructor
public class BukuCommandController {

    private final BukuCommandService bukuCommandService;

    @PostMapping
    public ResponseEntity<BukuCommand> createBuku(@Valid @RequestBody BukuCommand buku) {
        return ResponseEntity.status(201).body(bukuCommandService.createBuku(buku));
    }

    @PutMapping("/{id}")
    public ResponseEntity<BukuCommand> updateBuku(@PathVariable String id, @Valid @RequestBody BukuCommand buku) {
        return ResponseEntity.ok(bukuCommandService.updateBuku(id, buku));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBuku(@PathVariable String id) {
        bukuCommandService.deleteBuku(id);
        return ResponseEntity.ok("Buku berhasil dihapus");
    }
}
