package com.example.finnect.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class StockRewardRequest {
    
    private String title;
    private String description;
    private BigDecimal amount;
    
    // 주식 기본 정보
    private String stockType;
    private BigDecimal targetAmount;
    private BigDecimal sharePrice;
    private BigDecimal minInvestment;
    
    // 주식 발행 정보
    private Integer minInvestmentShares;
    private Long totalSharesToIssue;
    private BigDecimal preMoneyValuation;
    private BigDecimal faceValue;
    
    // 날짜 관련
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private LocalDate paymentDate;
    private LocalDate issueDate;
    private LocalDate deliveryDate;
}