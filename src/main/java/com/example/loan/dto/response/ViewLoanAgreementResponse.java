package com.example.loan.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter @Getter
public class ViewLoanAgreementResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String street;
    private String state;
    private String country;
    private BigDecimal loanAmount;
    private String repaymentPreference;
    private String loanTenure;
    private BigDecimal amountPerPaymentPeriod;
    private String errorMessage;


    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder();

        sb.append("\t\t\t\t\t\t\t\t\tLOAN AGREEMENT");
        sb.append("\n\nPARTIES\n");

        sb.append("\n=> This Loan Agreement (herein referred to as the \"AGREEMENT\" is entered into on\n")
                .append(LocalDate.now()).append(" (the \"Effective Date\")")
                .append(" by and between ").append(firstName).append(" ").append(lastName).append(", with an address of ")
                .append(street).append(" ").append(state).append(" ").append(country)
                .append("(hereinafter referred to as the \"BORROWER\"), and Company Ltd., with an address of 12A, Jones\n")
                .append("Crescent, MaryLand, Lagos (hereinafter referred to as the \"LENDER\").\n")
                .append("Both BORROWER and LENDER shall be collectively referred to as the \"PARTIES\".");

        sb.append("\n\nINFORMATION DETAILS OF PARTIES");
        sb.append("\n=> Details of BORROWER");
        sb.append("\n- Name ->").append(" ").append(firstName).append(" ").append(lastName);
        sb.append("\n- Email Address ->").append(" ").append(email);
        sb.append("\n- Phone Number ->").append(" ").append(phoneNumber);
        sb.append("\n- Contact Address ->").append(" ").append(street);
        sb.append("\n- Contact Address ->").append(" ").append(state);
        sb.append("\n- Contact Address ->").append(" ").append(country);

        sb.append("\n\n=> Details of LENDER\n" +
                "- Name -> Company LTD.\n" +
                "- Email Address -> contactcentre@company.com | customerservice@company.com\n" +
                "- Phone Number -> +234-8077223366 | 070-000111165 | +234-9006200000\n" +
                "- Contact Address -> 35A, Jones Crescent, MaryLand, Lagos.");

        sb.append("""


                TERMS OF AGREEMENT
                => The PARTIES agree that the loan information set below is accurate;""");
        sb.append("\n\n- Approved Loan Amount ->").append(" ").append(loanAmount);
        sb.append("\n- Loan Tenure ->").append(" ").append(loanTenure);
        sb.append("\n- Preferred Repayment Option ->").append(" ").append(repaymentPreference);
        sb.append("\n- Amount expected per payment period ->").append(" N").append(amountPerPaymentPeriod);
        sb.append("\n- Loan default fee -> 1% of existing balance per payment period.");

        sb.append("\n\n\nENDORSEMENTS\n\n\n");
        sb.append("".repeat(25)).append(" ".repeat(30)).append("".repeat(25)).append("\n");
        sb.append("\t").append(firstName).append(" ").append(lastName).append(" ".repeat(46)).append("Sherif Awofiranye\n");
        sb.append("\t").append("BORROWER").append(" ".repeat(45)).append("Team Lead, Company");

        return sb.toString();
    }
}
