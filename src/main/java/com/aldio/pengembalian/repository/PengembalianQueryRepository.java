package com.aldio.pengembalian.repository;

import com.aldio.pengembalian.model.PengembalianQuery;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PengembalianQueryRepository extends MongoRepository<PengembalianQuery, String>{

}
