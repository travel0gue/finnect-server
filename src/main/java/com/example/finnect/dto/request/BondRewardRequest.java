package com.example.finnect.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class BondRewardRequest {
    
    private String title;
    private String description;
    private BigDecimal amount;
    
    // 채권 기본 정보
    private String bondType;
    private BigDecimal targetAmount;
    private BigDecimal interestRate;
    private BigDecimal minInvestment;
    
    // 기간 관련
    private Integer interestPaymentCycle;
    private Integer maturityPeriod;
    
    // 날짜 관련
    private LocalDate maturityDate;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private LocalDate announcementDate;
    private LocalDate issueDate;
    private LocalDate paymentDate;
}