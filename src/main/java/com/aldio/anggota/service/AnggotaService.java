package com.aldio.anggota.service;

import com.aldio.anggota.model.Anggota;
import com.aldio.anggota.repository.AnggotaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnggotaService {
    @Autowired
    private AnggotaRepository anggotaRepository;

    // logika crate data
    public Anggota saveAnggota(Anggota anggota){
        return anggotaRepository.save(anggota);
    }

    // logila read data
    public List<Anggota> getAllAnggotas(){
        return anggotaRepository.findAll();
    }

    //logika read data bedasarkan id 
    public Anggota getAnggotaById(Long id) {
        return anggotaRepository.findById(id).orElse(null);
    }

    //logika mengedit data
    public Anggota updateAnggota(Long id, Anggota anggotaDetails){
        Anggota existingAnggota = anggotaRepository.findById(id).orElse(null);
        if (existingAnggota != null) {
            existingAnggota.setNim(anggotaDetails.getNim());
            existingAnggota.setNama(anggotaDetails.getNama());
            existingAnggota.setAlamat(anggotaDetails.getAlamat());
            existingAnggota.setJenis_kelamin(anggotaDetails.getJenis_kelamin());
        }
        return null;
    }

    // logika menghapus data
    public String deleAnggota(Long id){
        anggotaRepository.deleteById(id);
        return "anggota dengan id"+ id + "berhasil dihapus";
    }

}
