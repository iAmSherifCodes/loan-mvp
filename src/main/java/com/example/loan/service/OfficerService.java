package com.example.loan.service;

import com.example.loan.controller.OfficerLoginRequest;
import com.example.loan.dto.request.LoginRequest;
import com.example.loan.dto.request.RejectionRequest;
import com.example.loan.dto.request.UpdateLoanRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.dto.response.RejectLoanResponse;
import com.example.loan.dto.response.ReviewLoanApplicationResponse;
import com.example.loan.exceptions.IncorrectCredentials;
import com.example.loan.model.Customer;
import com.example.loan.model.Loan;

import java.util.List;

public interface OfficerService {
    LoanResponse login(OfficerLoginRequest loginRequest);
    List<Loan> viewLoanApplications();
    ReviewLoanApplicationResponse reviewLoanApplication (String email);
    LoanResponse acceptLoan(String customerId);
    RejectLoanResponse rejectLoan(RejectionRequest rejectionRequest);
    LoanResponse generateLoanAgreement(String customerId);
    LoanResponse updateLoanStatus(UpdateLoanRequest updateLoanRequest);
    Customer findCustomerById(String customerId);

}
