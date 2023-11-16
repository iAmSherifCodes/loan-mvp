package com.example.loan.service;

import com.example.loan.dto.request.LoginRequest;
import com.example.loan.dto.request.UpdateLoanRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.dto.response.ReviewLoanApplicationResponse;
import com.example.loan.model.Loan;
import com.example.loan.repository.CustomerRepository;
import com.example.loan.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service @AllArgsConstructor
public class LoanOfficerService implements OfficerService{
    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;
    private final CustomerService customerService;

    @Override
    public LoanResponse login(LoginRequest loginRequest) {
        return customerService.login(loginRequest);
    }

    @Override
    public List<Loan> viewLoanApplications() {
        return loanRepository.findAll();
    }

    @Override
    public ReviewLoanApplicationResponse reviewLoanApplication(String customerId) {
        return null;
    }

    @Override
    public LoanResponse acceptLoan(String loanId) {
        return null;
    }

    @Override
    public LoanResponse rejectLoan(String loanId) {
        return null;
    }

    @Override
    public LoanResponse generateLoanAgreement(String customerId) {
        return null;
    }

    @Override
    public LoanResponse updateLoanStatus(UpdateLoanRequest updateLoanRequest) {
        return null;
    }
}
