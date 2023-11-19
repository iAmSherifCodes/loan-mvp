package com.example.loan.service;

import com.example.loan.dto.request.LoginRequest;
import com.example.loan.dto.request.RejectionRequest;
import com.example.loan.dto.request.UpdateLoanRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.dto.response.RejectLoanResponse;
import com.example.loan.dto.response.ReviewLoanApplicationResponse;
import com.example.loan.enums.LoanStatus;
import com.example.loan.exceptions.CustomerNotFoundException;
import com.example.loan.exceptions.IncorrectCredentials;
import com.example.loan.model.Customer;
import com.example.loan.model.Loan;
import com.example.loan.repository.CustomerRepository;
import com.example.loan.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import static com.example.loan.enums.LoanStatus.APPROVED;
import static com.example.loan.enums.LoanStatus.REJECTED;
import static com.example.loan.utils.AppUtils.*;

@Service @AllArgsConstructor
public class LoanOfficerService implements OfficerService{

    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;


    @Override
    public LoanResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        return getLoanResponse(email, customerRepository, OFFICER_PASSWORD);
    }

    public static LoanResponse getLoanResponse(String email, CustomerRepository customerRepository, String officerPassword) {
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
        reviewLoanApplicationResponse.setEmail(foundCustomer.getEmail());
        return reviewLoanApplicationResponse;
    }

    @Override
    public LoanResponse acceptLoan(String customerId) {

        Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
        foundCustomer.getLoan().setLoanStatus(APPROVED);

        LoanResponse response = new LoanResponse();
        response.setMessage("YOU HAVE APPROVED "+ foundCustomer.getFirstName()+ " " +foundCustomer.getLastName()+ " LOAN APPLICATION.");
        return response;
    }

    @Override
    public RejectLoanResponse rejectLoan(RejectionRequest rejectionRequest) {
        String customerId= rejectionRequest.getCustomerId();
        String reason = rejectionRequest.getRejectionReason();

        Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
        foundCustomer.getLoan().setLoanStatus(REJECTED);

        RejectLoanResponse response = new RejectLoanResponse();
        response.setRejectionReason(reason);
        return response;
    }

    @Override
    public LoanResponse generateLoanAgreement(String customerId) {
        Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));

        String loanAgreement = "\t\t\t\t\t\t\t\t\tLOAN AGREEMENT" +
                "\n\nPARTIES\n" +
                "\n=> This Loan Agreement (herein referred to as the \"AGREEMENT\" is entered into on\n" +
                LocalDate.now() + " (the \"Effective Date\")" +
                " by and between " + foundCustomer.getFirstName() + " " + foundCustomer.getLastName() + ", with an address of " +
                foundCustomer.getAddress().getStreet() + " " + foundCustomer.getAddress().getState() + " " + foundCustomer.getAddress().getCountry() +
                "(hereinafter referred to as the \"BORROWER\"), and Company Ltd., with an address of 12A, Jones\n" +
                "Crescent, MaryLand, Lagos (hereinafter referred to as the \"LENDER\").\n" +
                "Both BORROWER and LENDER shall be collectively referred to as the \"PARTIES\"." +
                "\n\nINFORMATION DETAILS OF PARTIES" +
                "\n=> Details of BORROWER" +
                "\n- Name ->" + " " + foundCustomer.getFirstName() + " " + foundCustomer.getLastName() +
                "\n- Email Address ->" + " " + foundCustomer.getEmail() +
                "\n- Phone Number ->" + " " + foundCustomer.getPhoneNumber() +
                "\n- Contact Address ->" + " " + foundCustomer.getAddress().getStreet() +
                "\n- Contact Address ->" + " " + foundCustomer.getAddress().getState() +
                "\n- Contact Address ->" + " " + foundCustomer.getAddress().getCountry() +
                "\n\n=> Details of LENDER\n" +
                "- Name -> Company LTD.\n" +
                "- Email Address -> contactcentre@company.com | " + APP_EMAIL + "\n" +
                "- Phone Number -> +234-8077223366 | 070-000111165 | +234-9006200000\n" +
                "- Contact Address -> 35A, Jones Crescent, MaryLand, Lagos." +
                """ 


                        TERMS OF AGREEMENT
                        => The PARTIES agree that the loan information set below is accurate;""" +
                "\n\n- Approved Loan Amount ->" + " " + foundCustomer.getLoan().getLoanAmount() +
                "\n- Preferred Repayment Option ->" + " " + foundCustomer.getLoan().getRepaymentPreferences() +
                "\n- Loan default fee -> 1% of existing balance per payment period." +
                "\n\n\nENDORSEMENTS\n\n\n" +
                "".repeat(25) + " ".repeat(30) + "".repeat(25) + "\n" +
                "\t" + foundCustomer.getFirstName() + " " + foundCustomer.getLastName() + " ".repeat(46) + "Sherif Awofiranye\n" +
                "\t" + "BORROWER" + " ".repeat(45) + "Team Lead, Company";

        foundCustomer.getLoan().setLoanAgreement(loanAgreement);

        LoanResponse response = new LoanResponse();
        response.setMessage(AGREEMENT_MESSAGE);
        return response;

    }

    @Override
    public LoanResponse updateLoanStatus(UpdateLoanRequest updateLoanRequest) {
        String customerId = updateLoanRequest.getCustomerId();
        LoanStatus status = LoanStatus.valueOf(updateLoanRequest.getStatus().toUpperCase());

        Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(()-> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
        foundCustomer.getLoan().setLoanStatus(status);

        LoanResponse response = new LoanResponse();
        response.setMessage(UPDATE_MESSAGE);
        return response;
    }
}
