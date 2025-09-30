package com.aldio.pengembalian.controller;

import com.aldio.pengembalian.*;
import com.aldio.pengembalian.model.Pengembalian;
import com.aldio.pengembalian.service.PengembalianService;
import com.aldio.pengembalian.vo.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pengembalian")
public class PengembalianController {
    @Autowired
    private PengembalianService pengembalianService;

    @GetMapping("/{id}/details")
    public ResponseTemplate getPengembalianWithDetails(@PathVariable Long id){
        return pengembalianService.getPengembalianWithDetailsById(id);
    }


    @PostMapping
    public Pengembalian creatPengembalian(@RequestBody Pengembalian pengembalian){
        return pengembalianService.savePengembalian(pengembalian);
    }

    @GetMapping
    public List<Pengembalian> getAllPengembalians() {
        return pengembalianService.getAllPengembalian();
    }

    @GetMapping("/{id}")
    public Pengembalian getPengembalianById(@PathVariable Long id){
        return pengembalianService.getPengembalianById(id).orElse(null);
    }

    @PutMapping("/{id}")
    public Pengembalian updatePengembalian(@PathVariable Long id, @RequestBody Pengembalian pengembalianDetails){
        return pengembalianService.updatePengembalian(id, pengembalianDetails);
    }

    @DeleteMapping("/{id}")
    public String deletePengembalian(@PathVariable Long id) {
        return pengembalianService.deletePengembalian(id);
    }

    // === HITUNG LAMA PINJAM, TERLAMBAT, DENDA ===
    @PostMapping("/hitung")
    public pengambalianVo hitungPengembalian(@RequestBody Peminjaman peminjaman,
                                                   @RequestParam String tanggalDikembalikan) {
        return pengembalianService.hitungPengembalian(peminjaman, tanggalDikembalikan);
    }
}
