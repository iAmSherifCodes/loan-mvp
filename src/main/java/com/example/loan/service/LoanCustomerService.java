package com.example.loan.service;

import com.example.loan.dto.request.ApplyForLoanRequest;
import com.example.loan.dto.request.LoginRequest;
import com.example.loan.dto.request.RegisterRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.dto.response.ViewLoanAgreementResponse;
import com.example.loan.dto.response.ViewLoanApplicationStatusResponse;
import com.example.loan.enums.LoanStatus;
import com.example.loan.exceptions.CustomerNotFoundException;
import com.example.loan.exceptions.IncorrectCredentials;
import com.example.loan.model.Address;
import com.example.loan.model.Customer;
import com.example.loan.model.Loan;
import com.example.loan.repository.CustomerRepository;
import com.example.loan.repository.LoanRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

import static com.example.loan.service.LoanOfficerService.getLoanResponse;
import static com.example.loan.utils.AppUtils.*;

@Service @AllArgsConstructor
public class LoanCustomerService implements CustomerService{

    private final CustomerRepository customerRepository;
    private final LoanRepository loanRepository;

    @Override
    public LoanResponse register(RegisterRequest registerRequest) {
        String firstName = registerRequest.getFirstName();
        String lastName = registerRequest.getLastName();
        String password = registerRequest.getPassword();

        String phoneNumber = registerRequest.getPhoneNumber();
        String email = registerRequest.getEmail();
        String mobileNumber = registerRequest.getMobileNumber();
        String street = registerRequest.getStreet();
        String state = registerRequest.getState();
        String country = registerRequest.getCountry();

        Customer newCustomer = new Customer();
        newCustomer.setFirstName(firstName);
        newCustomer.setLastName(lastName);
        newCustomer.setEmail(email);
        newCustomer.setPassword(password);
        newCustomer.setPhoneNumber(phoneNumber);
        newCustomer.setMobileNumber(mobileNumber);
        Address address = Address
                .builder()
                .state(state)
                .street(street)
                .country(country)
                .build();
        newCustomer.setAddress(address);

        Customer savedCustomer = customerRepository.save(newCustomer);

        LoanResponse response = new LoanResponse();
        response.setMessage(String.valueOf(savedCustomer.getId()));

        return response;
    }

    @Override
    public LoanResponse login(LoginRequest loginRequest) {
        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        return getLoanResponse(email, customerRepository, password);

    }

    @Override
    public LoanResponse applyForLoan(ApplyForLoanRequest loanRequest) {
        String customerId = loanRequest.getCustomerId();

        Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException(CUSTOMER_NOT_FOUND));


        BigDecimal loanAmount = BigDecimal.valueOf(Long.parseLong(loanRequest.getLoanAmount()));
        String purpose = loanRequest.getPurpose();
        String repaymentPreference = loanRequest.getRepaymentPreference();

        Loan loan = new Loan();
        loan.setLoanStatus(LoanStatus.IN_PROGRESS);
        loan.setLoanAmount(loanAmount);
        loan.setPurpose(purpose);
        loan.setRepaymentPreferences(repaymentPreference);

        Loan newLoan = loanRepository.save(loan);
        foundCustomer.setLoan(newLoan);
        customerRepository.save(foundCustomer);
        LoanResponse response = new LoanResponse();
        response.setMessage("YOU'VE SUCCESSFULLY SENT A lOAN APPLICATION\nCHECK BACK LATER");

        return response;
    }

    @Override
    public ViewLoanApplicationStatusResponse viewLoanApplicationStatus(String customerId) {
        Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException(CUSTOMER_NOT_FOUND));

        ViewLoanApplicationStatusResponse response = new ViewLoanApplicationStatusResponse();
        response.setLoanPurpose(foundCustomer.getLoan().getPurpose());
        response.setLoanStatus(String.valueOf(foundCustomer.getLoan().getLoanStatus()));

        return response;
    }

    @Override
    public ViewLoanAgreementResponse viewLoanAgreement(String customerId) {
        Customer foundCustomer = customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFoundException(CUSTOMER_NOT_FOUND));
        ViewLoanAgreementResponse response = new ViewLoanAgreementResponse();
        if (foundCustomer.getLoan().getLoanStatus().equals(LoanStatus.APPROVED)) {
            response.setMessage(generateLoanAgreement(foundCustomer));
            return response;
        }
        response.setMessage("YOUR APPLICATION IS "+ foundCustomer.getLoan().getLoanStatus() + " PLEASE REACH OUT TO OUR CUSTOMER SERVICE FOR ANY SUPPORT - " + APP_EMAIL);
        return response;
    }

    private String generateLoanAgreement(Customer foundCustomer){
        final StringBuilder sb = new StringBuilder();

        sb.append("\t\t\t\t\t\t\t\t\tLOAN AGREEMENT");
        sb.append("\n\nPARTIES\n");

        sb.append("\n=> This Loan Agreement (herein referred to as the \"AGREEMENT\" is entered into on\n")
                .append(LocalDate.now()).append(" (the \"Effective Date\")")
                .append(" by and between ").append(foundCustomer.getFirstName()).append(" ").append(foundCustomer.getLastName()).append(", with an address of ")
                .append(foundCustomer.getAddress().getStreet()).append(" ").append(foundCustomer.getAddress().getState()).append(" ").append(foundCustomer.getAddress().getCountry())
                .append("(hereinafter referred to as the \"BORROWER\"), and Company Ltd., with an address of 12A, Jones\n")
                .append("Crescent, MaryLand, Lagos (hereinafter referred to as the \"LENDER\").\n")
                .append("Both BORROWER and LENDER shall be collectively referred to as the \"PARTIES\".");

        sb.append("\n\nINFORMATION DETAILS OF PARTIES");
        sb.append("\n=> Details of BORROWER");
        sb.append("\n- Name ->").append(" ").append(foundCustomer.getFirstName()).append(" ").append(foundCustomer.getLastName());
        sb.append("\n- Email Address ->").append(" ").append(foundCustomer.getEmail());
        sb.append("\n- Phone Number ->").append(" ").append(foundCustomer.getPhoneNumber());
        sb.append("\n- Contact Address ->").append(" ").append(foundCustomer.getAddress().getStreet());
        sb.append("\n- Contact Address ->").append(" ").append(foundCustomer.getAddress().getState());
        sb.append("\n- Contact Address ->").append(" ").append(foundCustomer.getAddress().getCountry());

        sb.append("\n\n=> Details of LENDER\n" +
                "- Name -> Company LTD.\n" +
                "- Email Address -> contactcentre@company.com | "+APP_EMAIL +"\n" +
                "- Phone Number -> +234-8077223366 | 070-000111165 | +234-9006200000\n" +
                "- Contact Address -> 35A, Jones Crescent, MaryLand, Lagos.");

        sb.append(""" 


                TERMS OF AGREEMENT
                => The PARTIES agree that the loan information set below is accurate;""");
        sb.append("\n\n- Approved Loan Amount ->").append(" ").append(foundCustomer.getLoan().getLoanAmount());
        sb.append("\n- Preferred Repayment Option ->").append(" ").append(foundCustomer.getLoan().getRepaymentPreferences());
        sb.append("\n- Loan default fee -> 1% of existing balance per payment period.");

        sb.append("\n\n\nENDORSEMENTS\n\n\n");
        sb.append("".repeat(25)).append(" ".repeat(30)).append("".repeat(25)).append("\n");
        sb.append("\t").append(foundCustomer.getFirstName()).append(" ").append(foundCustomer.getLastName()).append(" ".repeat(46)).append("Sherif Awofiranye\n");
        sb.append("\t").append("BORROWER").append(" ".repeat(45)).append("Team Lead, Company");

        return sb.toString();
    }
}