package com.aldio.anggota.controller;

import com.aldio.anggota.model.AnggotaQuery;
import com.aldio.anggota.service.AnggotaQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/anggota/query")
@RequiredArgsConstructor
public class AnggotaQueryController {

    private final AnggotaQueryService anggotaService;
    
    // route untuk membaca semua data pada database
    @GetMapping
    public ResponseEntity<List<AnggotaQuery>> getAllAnggotas(){
        anggotaService.getAllAnggotas();
        return ResponseEntity.ok(anggotaService.getAllAnggotas()); 
    } 

    // route untuk membaca data per id
    @GetMapping("/{id}")
    public ResponseEntity<AnggotaQuery> getAnggotaById(@PathVariable String id){
        anggotaService.getAnggotaById(id);
        return ResponseEntity.ok(anggotaService.getAnggotaById(id)); 
    }
}
