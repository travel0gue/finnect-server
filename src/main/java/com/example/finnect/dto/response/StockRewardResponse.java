package com.example.finnect.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class StockRewardResponse extends RewardResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal amount;
    private String rewardType;
    private String stockType;
    private BigDecimal targetAmount;
    private BigDecimal sharePrice;
    private BigDecimal minInvestment;
    private Integer minInvestmentShares;
    private Long totalSharesToIssue;
    private BigDecimal preMoneyValuation;
    private BigDecimal faceValue;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private LocalDate paymentDate;
    private LocalDate issueDate;
    private LocalDate deliveryDate;
}
