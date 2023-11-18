package com.example.loan.service;

import com.example.loan.dto.request.LoginRequest;
import com.example.loan.dto.request.UpdateLoanRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.dto.response.ReviewLoanApplicationResponse;
import com.example.loan.exceptions.CustomerNotFoundException;
import com.example.loan.exceptions.IncorrectCredentials;
import com.example.loan.model.Customer;
import com.example.loan.model.Loan;
import com.example.loan.repository.CustomerRepository;
import com.example.loan.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.loan.utils.AppUtils.*;

@Service @AllArgsConstructor
public class LoanOfficerService implements OfficerService{

    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;
    private final CustomerService customerService;


    // TODO
    // OFFICER SHOULD LOGIN WITH THEIR NAME AND EMAIL
    // EMAIL IS SENT TO THEM CONTAINING A ONE-TIME PASSWORD
    // AFTER 10MINS PASSWORD IS REVOKED
    @Override
    public LoanResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        return getLoanResponse(email, customerRepository, OFFICER_PASSWORD);
    }

    static LoanResponse getLoanResponse(String email, CustomerRepository customerRepository, String officerPassword) {
        Customer foundCustomer = customerRepository.findByEmail(email).orElseThrow(()-> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));

        LoanResponse response = new LoanResponse();
        if (foundCustomer.getPassword().equals(officerPassword)){
            response.setMessage(foundCustomer.getId());
            return response;
        }
        throw new IncorrectCredentials(INCORRECT_PASSWORD);
    }

    @Override
    public List<Loan> viewLoanApplications() {
        return loanRepository.findAll();
    }

    @Override
    public ReviewLoanApplicationResponse reviewLoanApplication(String email) {
        Customer foundCustomer = customerRepository.findByEmail(email).orElseThrow(()-> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
        ReviewLoanApplicationResponse reviewLoanApplicationResponse = new ReviewLoanApplicationResponse();
        reviewLoanApplicationResponse.setLastName(foundCustomer.getLastName());
        reviewLoanApplicationResponse.setFirstName(foundCustomer.getFirstName());
        reviewLoanApplicationResponse.setLoanAmount(String.valueOf(foundCustomer.getLoan().getLoanAmount()));
        reviewLoanApplicationResponse.setLoanPurpose(foundCustomer.getLoan().getPurpose());
        reviewLoanApplicationResponse.setRepaymentPreference(foundCustomer.getLoan().getRepaymentPreferences());
        reviewLoanApplicationResponse.setMobileNumber(foundCustomer.getMobileNumber());
        reviewLoanApplicationResponse.setLoanId(foundCustomer.getLoan().getId());
        return reviewLoanApplicationResponse;
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
        String loanId = updateLoanRequest.getLoanId();
        String status = updateLoanRequest.getStatus();


        return null;
    }
}
