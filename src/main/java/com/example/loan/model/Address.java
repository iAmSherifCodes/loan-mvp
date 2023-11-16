package com.example.loan.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Builder
public class Address {
    private String street;
    private String state;
    private String country;
}
