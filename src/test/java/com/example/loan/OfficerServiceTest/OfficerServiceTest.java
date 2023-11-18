package com.example.loan.OfficerServiceTest;

import com.example.loan.dto.request.ApplyForLoanRequest;
import com.example.loan.dto.request.LoginRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.dto.response.ReviewLoanApplicationResponse;
import com.example.loan.service.CustomerService;
import com.example.loan.service.OfficerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest @Slf4j
public class OfficerServiceTest {

    @Autowired
    private OfficerService officerService;
    @Autowired
    private CustomerService customerService;

    @Test
    void testOfficerCanLogin(){
        String email = "";
    }

    @Test
    void officerCanViewLoanApplications(){
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
        assertThat(officerService.viewLoanApplications().size()).isGreaterThan(1);
    }

    @Test
    void officerCanReviewLoanApplications(){
        ReviewLoanApplicationResponse reviewLoanApplicationResponse = officerService.reviewLoanApplication("mymail@gmail.com");
        assertThat(reviewLoanApplicationResponse).isNotNull();
    }


}
