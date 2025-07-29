package com.example.finnect.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class LoanRepaymentRewardRequest {
    
    private String title;
    private String description;
    private BigDecimal amount;
    
    private BigDecimal interestRate;
    private Integer loanTermMonths;
    private String repaymentFrequency;
    private Integer gracePeriodMonths;
    private boolean earlyRepaymentAllowed;
    private boolean collateralRequired;
    private String collateralDescription;
}