package com.aldio.pengembalian.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.aldio.pengembalian.model.PengembalianCommand;

@Repository
public interface PengembalianCommandRepository extends JpaRepository<PengembalianCommand, String> {
    
}
