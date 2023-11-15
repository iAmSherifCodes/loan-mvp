package com.example.loan.repository;

import com.example.loan.model.Loan;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface LoanRepository extends MongoRepository<Loan, String> {
}
