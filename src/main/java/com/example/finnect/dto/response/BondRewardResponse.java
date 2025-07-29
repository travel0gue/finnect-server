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
public class BondRewardResponse extends RewardResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal amount;
    private String rewardType;
    private String bondType;
    private BigDecimal targetAmount;
    private BigDecimal interestRate;
    private BigDecimal minInvestment;
    private Integer interestPaymentCycle;
    private Integer maturityPeriod;
    private LocalDate maturityDate;
    private LocalDate subscriptionStartDate;
    private LocalDate subscriptionEndDate;
    private LocalDate announcementDate;
    private LocalDate issueDate;
    private LocalDate paymentDate;
}
