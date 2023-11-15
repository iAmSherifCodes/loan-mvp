package com.example.loan.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ApplyForLoanRequest {
    private String customerId;
    private String loanAmount;
    private String purpose;
    private String repaymentPreference;
}
