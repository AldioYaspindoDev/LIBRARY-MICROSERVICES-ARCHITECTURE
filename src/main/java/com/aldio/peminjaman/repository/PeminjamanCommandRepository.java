package com.aldio.peminjaman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aldio.peminjaman.model.PeminjamanCommand;

@Repository
public interface PeminjamanCommandRepository extends JpaRepository<PeminjamanCommand, String> {
    
}



