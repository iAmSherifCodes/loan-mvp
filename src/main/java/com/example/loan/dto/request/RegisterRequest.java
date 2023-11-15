package com.example.loan.dto.request;


import com.example.loan.model.Address;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String phoneNumber;
    private String email;
    private String mobileNumber;
    private String street;
    private String state;
    private String country;
}
