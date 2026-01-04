package com.aldio.anggota.repository;


import com.aldio.anggota.model.AnggotaQuery;
import org.springframework.data.mongodb.repository.MongoRepository; // PERBAIKAN: Import MongoRepository
import org.springframework.stereotype.Repository;


@Repository
public interface AnggotaQueryRepository extends MongoRepository<AnggotaQuery, String>{
 
}
