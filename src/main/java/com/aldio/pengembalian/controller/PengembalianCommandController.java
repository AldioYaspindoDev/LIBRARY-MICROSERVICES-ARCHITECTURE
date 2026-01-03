package com.aldio.pengembalian.controller;

import com.aldio.pengembalian.model.PengembalianCommand;
import com.aldio.pengembalian.service.PengembalianCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/pengembalian/command")
@RequiredArgsConstructor
public class PengembalianCommandController {
    @Autowired
    private final PengembalianCommandService pengembalianService;

    @PostMapping
    public ResponseEntity<String> creatPengembalian(@RequestBody PengembalianCommand pengembalian){
        pengembalianService.createPengembalian(pengembalian);
        return ResponseEntity.ok("buku berhasil dibuat");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePengembalian(@PathVariable String id, @RequestBody PengembalianCommand pengembalianDetails){
        pengembalianService.updatePengembalian(id, pengembalianDetails);
        return ResponseEntity.ok("buku berhasil di update");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePengembalian(@PathVariable String id) {
        pengembalianService.deletePengembalian(id);
        return ResponseEntity.ok("buku berhasil dihapus");
    }
}
