package com.aldio.anggota.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aldio.anggota.model.AnggotaCommand;

@Repository
public interface AnggotaCommandRepository extends JpaRepository<AnggotaCommand, String> {
    
        
}

