package com.aldio.anggota.service;

import com.aldio.anggota.model.AnggotaQuery;
import com.aldio.anggota.repository.AnggotaQueryRepository; // PERBAIKAN: Gunakan repository untuk MongoDB
import com.mongodb.lang.NonNull;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnggotaQueryService {

    private final AnggotaQueryRepository anggotaQueryRepository;
     // logila read data
    public List<AnggotaQuery> getAllAnggotas(){
        return anggotaQueryRepository.findAll();
    }

    //logika read data bedasarkan id 
    public AnggotaQuery getAnggotaById(String id) {
        return anggotaQueryRepository.findById(id).orElse(null);
    }
   
}
