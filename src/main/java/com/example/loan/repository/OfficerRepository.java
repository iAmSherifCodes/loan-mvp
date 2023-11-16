package com.example.loan.repository;

import com.example.loan.model.Officer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfficerRepository extends MongoRepository<Officer, String> {
}
