package com.aldio.peminjaman.repository;
import com.aldio.peminjaman.model.PeminjamanQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeminjamanQueryRepository extends MongoRepository<PeminjamanQuery, String>{
    
} 
