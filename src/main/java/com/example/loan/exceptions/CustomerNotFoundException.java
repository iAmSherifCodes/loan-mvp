package com.example.loan.exceptions;

public class CustomerNotFoundException extends RuntimeException{
    private String message;

    public CustomerNotFoundException(String message){
        super(message);
    }
}
