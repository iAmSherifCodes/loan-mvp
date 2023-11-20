package com.example.loan.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Setter @Getter
public class Customer{
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String mobileNumber;
    private Address address;
    private Loan loan;
}
