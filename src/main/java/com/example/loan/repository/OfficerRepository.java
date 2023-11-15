package com.example.loan.repository;

import com.example.loan.model.Officer;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface OfficerRepository extends MongoRepository<Officer, String> {
}
