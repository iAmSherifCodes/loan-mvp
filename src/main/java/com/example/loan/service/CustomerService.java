package com.example.loan.service;

import com.example.loan.dto.request.ApplyForLoanRequest;
import com.example.loan.dto.request.LoginRequest;
import com.example.loan.dto.request.RegisterRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.dto.response.ViewLoanAgreementResponse;
import com.example.loan.dto.response.ViewLoanApplicationStatusResponse;

public interface CustomerService {
    LoanResponse register (RegisterRequest registerRequest);
    LoanResponse login (LoginRequest loginRequest);
    LoanResponse applyForLoan(ApplyForLoanRequest loanRequest);
    ViewLoanApplicationStatusResponse viewLoanApplicationStatus();
    ViewLoanAgreementResponse viewLoanAgreement();
}
