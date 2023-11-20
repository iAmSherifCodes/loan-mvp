package com.example.loan.exceptions;

public class NoLoanAvailable extends RuntimeException{
    public NoLoanAvailable(String message){
        super(message);
    }
}
