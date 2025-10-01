package com.aldio.buku.controller;

import com.aldio.buku.model.BukuQuery;
import com.aldio.buku.service.BukuQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/buku/query")
@RequiredArgsConstructor
public class BukuQueryController {

    private final BukuQueryService bukuQueryService;

    @GetMapping
    public ResponseEntity<List<BukuQuery>> getAllBukus() {
        return ResponseEntity.ok(bukuQueryService.getAllBukus());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BukuQuery> getBukuById(@PathVariable String id) {
        BukuQuery buku = bukuQueryService.getBukuById(id);
        return ResponseEntity.ok(buku);
    }
}
