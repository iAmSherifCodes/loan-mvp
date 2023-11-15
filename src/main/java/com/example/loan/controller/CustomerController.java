package com.example.loan.controller;

import com.example.loan.dto.request.RegisterRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController  @RequestMapping("/") @CrossOrigin("*") @AllArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
    @PostMapping("register")
    public ResponseEntity<LoanResponse> registerCustomer(@RequestBody RegisterRequest registerRequest){
        return new ResponseEntity<>(customerService.register(registerRequest), HttpStatus.OK);
    }
}
