package com.example.loan.controller;

import com.example.loan.model.Loan;
import com.example.loan.service.OfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController @RequestMapping("/") @CrossOrigin("*")
public class OfficerController {

    @Autowired
    private OfficerService officerService;

    @GetMapping("/all-loans")
    public ResponseEntity<List<Loan>> allLoans(){
        return ResponseEntity.ok(officerService.viewLoanApplications());
    }

}
