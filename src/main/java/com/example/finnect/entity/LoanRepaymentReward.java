package com.example.finnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "loan_repayment_rewards")
@DiscriminatorValue("LOAN")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class LoanRepaymentReward extends Reward {
    
    @Column(name = "interest_rate", precision = 5, scale = 2)
    private BigDecimal interestRate;
    
    @Column(name = "loan_term_months")
    private Integer loanTermMonths;
    
    @Column(name = "repayment_frequency", length = 20)
    private String repaymentFrequency; // MONTHLY, QUARTERLY, ANNUALLY
    
    @Column(name = "grace_period_months")
    private Integer gracePeriodMonths;
    
    @Column(name = "early_repayment_allowed")
    private boolean earlyRepaymentAllowed;
    
    @Column(name = "collateral_required")
    private boolean collateralRequired;
    
    @Column(name = "collateral_description")
    private String collateralDescription;
}