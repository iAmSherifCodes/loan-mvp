package com.example.loan.service;

import com.example.loan.dto.request.LoginRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.model.Loan;

import java.util.List;

public interface OfficerService {
    LoanResponse login(LoginRequest loginRequest);
    List<Loan> viewLoanApplications();

}
