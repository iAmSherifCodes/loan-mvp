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

import static com.example.loan.utils.AppUtils.CUSTOMER_NOT_FOUND;
import static com.example.loan.utils.AppUtils.INCORRECT_PASSWORD;


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

        Customer foundCustomer = customerRepository.findByEmail(email).orElseThrow(()-> new CustomerNotFoundException(CUSTOMER_NOT_FOUND));

        LoanResponse response = new LoanResponse();
        if (foundCustomer.getPassword().equals(password)){
            response.setMessage(foundCustomer.getId());
            return response;
        }

            throw new IncorrectCredentials(INCORRECT_PASSWORD);

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
    public ViewLoanAgreementResponse viewLoanAgreement() {
        return null;
    }
}
