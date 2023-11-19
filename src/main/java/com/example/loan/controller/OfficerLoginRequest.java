package com.example.loan.controller;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class OfficerLoginRequest {
    private String firstName;
    private String lastName;
    private String password;
}
