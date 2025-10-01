package com.aldio.buku.repository;

import com.aldio.buku.model.BukuQuery;
import org.springframework.data.mongodb.repository.MongoRepository; // PERBAIKAN: Import MongoRepository
import org.springframework.stereotype.Repository;


@Repository
// PERBAIKAN: Harus extends MongoRepository untuk berinteraksi dengan MongoDB
public interface BukuQueryRepository extends MongoRepository<BukuQuery, String> {

}