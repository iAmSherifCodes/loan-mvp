package com.example.loan.exceptions;

public class IncorrectCredentials extends RuntimeException {
    public IncorrectCredentials(String message) {
        super(message);
    }
}
