package com.aldio.anggota.controller;

import com.aldio.anggota.model.Anggota;
import com.aldio.anggota.service.AnggotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import  java.util.List;


@RestController
@RequestMapping("/api/anggota")
public class AnggotaController {
    @Autowired
    private AnggotaService anggotaService;

    // route untuk create data
    @PostMapping
    public Anggota createAnggota(@RequestBody Anggota anggota){
        return anggotaService.saveAnggota(anggota);
    }
    
    // route untuk membaca semua data pada database
    @GetMapping
    public List<Anggota> getAllAnggotas(){
        return anggotaService.getAllAnggotas();
    } 

    // route untuk membaca data per id
    @GetMapping("/{id}")
    public Anggota getAnggotaById(@PathVariable Long id){
        return anggotaService.getAnggotaById(id);
    }

    // route untuk mengupdate data
    @PutMapping("/{id}")
    public Anggota updateAnggota(@PathVariable Long id, @RequestBody Anggota AnggotaDetails){
        return anggotaService.updateAnggota(id, AnggotaDetails);
    }

    // route untuk menghapus data anggota
    @DeleteMapping("/{id}")
    public String deleteAnggota(Long id){
        return anggotaService.deleAnggota(id);
    }
}
