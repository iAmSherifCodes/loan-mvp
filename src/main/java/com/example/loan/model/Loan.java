package com.example.loan.model;

import com.example.loan.enums.LoanStatus;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.UUID;

@Document
@Getter @Setter
public class Loan {
    @Id
    private String id;
    private BigDecimal loanAmount;
    private String purpose;
    private String repaymentPreferences;
    private LoanStatus loanStatus;
}
