package com.example.loan.service;

import com.example.loan.dto.request.LoginRequest;
import com.example.loan.dto.request.UpdateLoanRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.dto.response.ReviewLoanApplicationResponse;
import com.example.loan.model.Loan;

import java.util.List;

public interface OfficerService {
    LoanResponse login(LoginRequest loginRequest);
    List<Loan> viewLoanApplications();
    ReviewLoanApplicationResponse reviewLoanApplication (String email);
    LoanResponse acceptLoan(String loanId);
    LoanResponse rejectLoan(String loanId);
    LoanResponse generateLoanAgreement(String customerId);
    LoanResponse updateLoanStatus(UpdateLoanRequest updateLoanRequest);

}
