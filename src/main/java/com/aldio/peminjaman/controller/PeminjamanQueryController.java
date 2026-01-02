package com.aldio.peminjaman.controller;
import com.aldio.peminjaman.model.PeminjamanQuery;
import com.aldio.peminjaman.service.PeminjamanQueryService;
import com.aldio.peminjaman.vo.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/peminjaman/query")
@RequiredArgsConstructor
public class PeminjamanQueryController {
    private final PeminjamanQueryService peminjamanService;

    @GetMapping("/{id}/details")
    public ResponseTemplate getPeminjamanWithDetails(@PathVariable String id) {
        return peminjamanService.getPeminjamanWithDetailsById(id);
    }


    @GetMapping
    public ResponseEntity<List<PeminjamanQuery>> getAllPeminjaman(){
        return ResponseEntity.ok(peminjamanService.getAllPeminjaman());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PeminjamanQuery> getPeminjamanById(@PathVariable String id){
        PeminjamanQuery peminjaman = peminjamanService.getPeminjamanById(id);
        return ResponseEntity.ok(peminjaman);
    }
}
