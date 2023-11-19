package com.example.loan.OfficerServiceTest;

import com.example.loan.controller.OfficerLoginRequest;
import com.example.loan.dto.request.ApplyForLoanRequest;
import com.example.loan.dto.request.LoginRequest;
import com.example.loan.dto.request.RejectionRequest;
import com.example.loan.dto.request.UpdateLoanRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.dto.response.RejectLoanResponse;
import com.example.loan.dto.response.ReviewLoanApplicationResponse;
import com.example.loan.enums.LoanStatus;
import com.example.loan.exceptions.IncorrectCredentials;
import com.example.loan.model.Customer;
import com.example.loan.model.Loan;
import com.example.loan.service.CustomerService;
import com.example.loan.service.OfficerService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest @Slf4j
public class OfficerServiceTest {

    @Autowired
    private OfficerService officerService;
    @Autowired
    private CustomerService customerService;

    @Test
    void testOfficerCanLogin(){
        OfficerLoginRequest loginRequest = new OfficerLoginRequest();
        loginRequest.setFirstName("ADEOLA");
        loginRequest.setLastName("MARIAM");
        String password = "MYMAILPASSWORD";
        loginRequest.setPassword(password);

        LoanResponse response = officerService.login(loginRequest);
        assertThat(response).isNotNull();
    }

    @Test
    void testOfficerCannotLogin(){
        OfficerLoginRequest loginRequest = new OfficerLoginRequest();
        loginRequest.setFirstName("ADEOLA");
        loginRequest.setLastName("MARIAM");
        String password = "MYMAILPASSWOR";
        loginRequest.setPassword(password);


        assertThatThrownBy(()-> officerService.login(loginRequest)).isInstanceOf(IncorrectCredentials.class);
    }

    @Test
    void officerCanViewLoanApplications(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("mymail@gmail.com");
        loginRequest.setPassword("0000");

        LoanResponse loginResponse = customerService.login(loginRequest);
        log.info("{{{{{{}}}}}}=>>>>>", loginResponse.getMessage());
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

    @Test
    void officerCanAcceptLoan(){
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

        LoanResponse response1 = officerService.acceptLoan(customerId);
        assertThat(response1.getMessage()).isNotNull();

    }

    @Test
    void officerCanRejectLoan(){
//        String customerId = "65551f51683cff18f46449a6";
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

        RejectionRequest rejectionRequest = new RejectionRequest();
        rejectionRequest.setCustomerId(customerId);
        rejectionRequest.setRejectionReason("LOAN REPAYMENT REFERENCE NOT REALISTIC");

        RejectLoanResponse rejectLoanResponse = officerService.rejectLoan(rejectionRequest);
        assertThat(rejectLoanResponse.getRejectionReason()).isNotNull();

        Customer customer = officerService.findCustomerById(customerId);
        LoanStatus customerLoanStatus = customer.getLoan().getLoanStatus();

        assertThat(customerLoanStatus).isNotNull();
    }

    @Test
    void officerCanGenerateLoanAgreement(){
        String customerId = "65551f51683cff18f46449a6";

        LoanResponse response = officerService.generateLoanAgreement(customerId);

        assertThat(response).isNotNull();

        Customer customer = officerService.findCustomerById(customerId);

        String customerLoanAgreement = customer.getLoan().getLoanAgreement();
        assertThat(customerLoanAgreement).isNotNull();

    }

    @Test
    void officerCanUpdateLoanStatusToRejected(){
        String customerId = "65551f51683cff18f46449a6";
        UpdateLoanRequest updateLoanRequest = new UpdateLoanRequest();
        updateLoanRequest.setCustomerId(customerId);
        updateLoanRequest.setStatus("rejected");
        LoanResponse loanResponse = officerService.updateLoanStatus(updateLoanRequest);

        assertThat(loanResponse).isNotNull();
        Customer customer = officerService.findCustomerById(customerId);

        String customerLoanStatus = String.valueOf(customer.getLoan().getLoanStatus());

        assertThat(customerLoanStatus).isEqualTo("REJECTED");

    }

    @Test
    void officerCanUpdateLoanStatusToAccepted(){
        String customerId = "65551f51683cff18f46449a6";
        UpdateLoanRequest updateLoanRequest = new UpdateLoanRequest();
        updateLoanRequest.setCustomerId(customerId);
        updateLoanRequest.setStatus("accepted");
        LoanResponse loanResponse = officerService.updateLoanStatus(updateLoanRequest);

        assertThat(loanResponse).isNotNull();
        Customer customer = officerService.findCustomerById(customerId);

        String customerLoanStatus = String.valueOf(customer.getLoan().getLoanStatus());

        assertThat(customerLoanStatus).isEqualTo("ACCEPTED");
    }


}
