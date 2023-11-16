package com.example.loan.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class ReviewLoanApplicationResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String mobileNumber;
    private String loanId;
    private String loanAmount;
    private String loanPurpose;
    private String repaymentPreference;
}
