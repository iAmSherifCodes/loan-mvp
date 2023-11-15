package com.example.loan.customerService;

import com.example.loan.dto.request.ApplyForLoanRequest;
import com.example.loan.dto.request.LoginRequest;
import com.example.loan.dto.request.RegisterRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.service.CustomerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest @Slf4j
public class CustomerServiceTest {

    @Autowired
    private CustomerService customerService;

    @Test
    void testCustomerCanRegister(){
         String firstName = "Sherif";
         String lastName = "Ola";
         String password ="0000";
         String phoneNumber = "898989899";
         String email = "mymail@gmail.com";
         String mobileNumber = "9090900";
         String street = "Soji";
         String state = "Yaba";
         String country = "Nigeria";
        RegisterRequest registerRequest = new RegisterRequest();
         registerRequest.setCountry(country);
         registerRequest.setFirstName(firstName);
         registerRequest.setLastName(lastName);
         registerRequest.setPassword(password);
         registerRequest.setPhoneNumber(phoneNumber);
         registerRequest.setEmail(email);
         registerRequest.setMobileNumber(mobileNumber);
         registerRequest.setState(state);
         registerRequest.setStreet(street);
         LoanResponse response = customerService.register(registerRequest);

         log.info("{{{{}}}}}++++++====>",response.getMessage());
         assertThat(response).isNotNull();
    }


    @Test
    void customerCanApplyForLoan(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("mymail@gmail.com");
        loginRequest.setPassword("0000");

        LoanResponse loginResponse = customerService.login(loginRequest);

        String customerId = loginResponse.getMessage();
        String loanAmount = "69000000";
        String purpose = "SCHOLARSHIP";
        String repaymentPreference = "MONTHLY REPAYMENT";

        ApplyForLoanRequest request = new ApplyForLoanRequest();
        request.setPurpose(purpose);
        request.setCustomerId(customerId);
        request.setLoanAmount(loanAmount);
        request.setRepaymentPreference(repaymentPreference);

        LoanResponse response = customerService.applyForLoan(request);
        assertThat(response).isNotNull();
        log.info(response.getMessage());
    }
}
