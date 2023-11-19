package com.example.loan.dto.request;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class RejectionRequest {
    private String customerId;
    private String rejectionReason;
}
