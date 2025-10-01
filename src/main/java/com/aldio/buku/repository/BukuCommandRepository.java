package com.aldio.buku.repository;

import com.aldio.buku.model.BukuCommand;
import org.springframework.data.jpa.repository.JpaRepository; // PERBAIKAN: Import JpaRepository
import org.springframework.stereotype.Repository;

@Repository
// PERBAIKAN: Harus extends JpaRepository untuk berinteraksi dengan PostgreSQL
public interface BukuCommandRepository extends JpaRepository<BukuCommand, String> {

}