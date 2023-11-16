package com.example.loan.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class UpdateLoanRequest {
    private String loanId;
    private String status;
}
