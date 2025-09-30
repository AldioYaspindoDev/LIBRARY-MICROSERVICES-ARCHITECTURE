package com.aldio.buku.controller;

import com.aldio.buku.model.Buku;
import com.aldio.buku.service.BukuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buku")
public class BukuController {
    @Autowired
    private BukuService bukuService;

    // route untuk create data
    @PostMapping
    public ResponseEntity<?> createBuku(@RequestBody Buku buku) {
        try {
            if (buku == null) {
                return ResponseEntity.badRequest().body("Request body cannot be null");
            }
            Buku savedBuku = bukuService.saveBuku(buku);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBuku);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error creating book: " + e.getMessage());
        }
    }

    // route untuk membaca semua data pada database
    @GetMapping
    public ResponseEntity<?> getAllBukus() {
        try {
            List<Buku> bukus = bukuService.getAllBukus();
            return ResponseEntity.ok(bukus);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching books: " + e.getMessage());
        }
    }

    // route untuk membaca data per id
    @GetMapping("/{id}")
    public ResponseEntity<?> getBukuById(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body("Invalid ID");
            }
            Buku buku = bukuService.getBukuById(id);
            if (buku != null) {
                return ResponseEntity.ok(buku);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error fetching book: " + e.getMessage());
        }
    }

    // route untuk mengupdate data
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBuku(@PathVariable Long id, @RequestBody Buku bukuDetails) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body("Invalid ID");
            }
            if (bukuDetails == null) {
                return ResponseEntity.badRequest().body("Request body cannot be null");
            }
            Buku updatedBuku = bukuService.updateBuku(id, bukuDetails);
            if (updatedBuku != null) {
                return ResponseEntity.ok(updatedBuku);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error updating book: " + e.getMessage());
        }
    }

    // route untuk menghapus data buku
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBuku(@PathVariable Long id) {
        try {
            if (id == null || id <= 0) {
                return ResponseEntity.badRequest().body("Invalid ID");
            }
            String result = bukuService.deleteBuku(id);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting book: " + e.getMessage());
        }
    }
}