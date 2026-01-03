package com.aldio.pengembalian.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.aldio.pengembalian.model.PengembalianQuery;
import com.aldio.pengembalian.service.PengembalianQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/pengembalian/query")
@RequiredArgsConstructor
public class PengebalianQueryController {
    
    private final PengembalianQueryService pengembalianService;

    @GetMapping("/{id}/details")
    public ResponseEntity<String> getPengembalianWithDetails(@PathVariable String id){
        pengembalianService.getPengembalianWithDetailsById(id);
        return ResponseEntity.ok("berhasil mengambil data pengembalian");
    } 
    
    @GetMapping
    public ResponseEntity<List<PengembalianQuery>> getAllPengembalian() {
        return ResponseEntity.ok(pengembalianService.getAllPengembalian());
    }

    @GetMapping("/{id}")
    public PengembalianQuery getPengembalianById(@PathVariable String id){
        return pengembalianService.getPengembalianById(id).orElse(null);
    }

}
