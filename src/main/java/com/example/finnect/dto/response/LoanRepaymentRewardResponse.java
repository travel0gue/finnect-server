package com.example.finnect.dto.response;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LoanRepaymentRewardResponse extends RewardResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal amount;
    private String rewardType;
    private BigDecimal interestRate;
    private Integer loanTermMonths;
    private String repaymentFrequency;
    private Integer gracePeriodMonths;
    private boolean earlyRepaymentAllowed;
    private boolean collateralRequired;
    private String collateralDescription;
}

