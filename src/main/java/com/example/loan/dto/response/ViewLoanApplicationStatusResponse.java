package com.example.loan.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ViewLoanApplicationStatusResponse {
    private String loanPurpose;
    private String loanStatus;
}
