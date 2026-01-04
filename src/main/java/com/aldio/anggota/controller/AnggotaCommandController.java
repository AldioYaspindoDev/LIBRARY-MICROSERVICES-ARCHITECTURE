package com.aldio.anggota.controller;

import com.aldio.anggota.model.AnggotaCommand;
import com.aldio.anggota.service.AnggotaCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/anggota/command")
@RequiredArgsConstructor
public class AnggotaCommandController {

    private final AnggotaCommandService anggotaService;


    // route untuk create data
    @PostMapping
    public ResponseEntity<AnggotaCommand> saveAnggota(@RequestBody AnggotaCommand anggota){
        anggotaService.saveAnggota(anggota);
        return ResponseEntity.ok(anggotaService.saveAnggota(anggota));
    }
    
    // route untuk mengupdate data
    @PutMapping("/{id}")
    public ResponseEntity<AnggotaCommand> updateAnggota(@PathVariable String id, @RequestBody AnggotaCommand AnggotaDetails){
        anggotaService.updateAnggota(id, AnggotaDetails);
        return ResponseEntity.ok(anggotaService.updateAnggota(id, AnggotaDetails)); 
    }

    // route untuk menghapus data anggota
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnggota(String id){
        anggotaService.deleteAnggota(id);
        return ResponseEntity.ok("berhasil menghapus data"); 
    }
}
