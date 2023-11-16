package com.example.loan.controller;

import com.example.loan.dto.request.LoginRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/") @AllArgsConstructor
public class LoginController {
    private final CustomerService customerService;

    @PostMapping("login")
    public ResponseEntity<LoanResponse> login (@RequestBody LoginRequest request){
        return ResponseEntity.ok(customerService.login(request));
    }
}
