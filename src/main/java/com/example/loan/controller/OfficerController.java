package com.example.loan.controller;

import com.example.loan.dto.request.RejectionRequest;
import com.example.loan.dto.request.UpdateLoanRequest;
import com.example.loan.dto.response.LoanResponse;
import com.example.loan.dto.response.RejectLoanResponse;
import com.example.loan.dto.response.ReviewLoanApplicationResponse;
import com.example.loan.model.Loan;
import com.example.loan.service.OfficerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController @RequestMapping("/") @CrossOrigin("*")
public class OfficerController {

    @Autowired
    private OfficerService officerService;

    @PostMapping("login")
    public ResponseEntity<LoanResponse> login(@RequestBody OfficerLoginRequest loginRequest){
        return ResponseEntity.ok(officerService.login(loginRequest));
    }

    @GetMapping("/all-loans")
    public ResponseEntity<List<Loan>> allLoans(){
        return ResponseEntity.ok(officerService.viewLoanApplications());
    }

    @GetMapping("/review-loan/{email}")
    public ResponseEntity<ReviewLoanApplicationResponse> reviewLoan(@PathVariable String email){
        return ResponseEntity.ok(officerService.reviewLoanApplication(email));
    }

    @PostMapping("accept-loan/{customerId}")
    public ResponseEntity<LoanResponse> acceptLoan(@PathVariable String customerId){
        return new ResponseEntity<>(officerService.acceptLoan(customerId), HttpStatus.OK);
    }

    @PostMapping("reject-loan")
    public ResponseEntity<RejectLoanResponse> rejectLoan(@RequestBody RejectionRequest rejectionRequest){
        return new ResponseEntity<>(officerService.rejectLoan(rejectionRequest), HttpStatus.OK);
    }

    @PostMapping("generate-agreement/{customerId}")
    public ResponseEntity<LoanResponse> generateLoanAgreement(@PathVariable String customerId){
        return new ResponseEntity<>(officerService.generateLoanAgreement(customerId), HttpStatus.OK);
    }

    @PostMapping("updateLoanStatus")
    public ResponseEntity<LoanResponse> updateLoanStatus(@RequestBody UpdateLoanRequest updateLoanRequest){
        return new ResponseEntity<>(officerService.updateLoanStatus(updateLoanRequest), HttpStatus.OK);
    }



}
