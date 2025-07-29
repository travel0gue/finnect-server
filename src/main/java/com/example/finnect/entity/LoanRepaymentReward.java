package com.example.finnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import com.example.finnect.dto.request.LoanRepaymentRewardRequest;

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

    public static LoanRepaymentReward createDraft(Project project) {
        return LoanRepaymentReward.builder()
                .project(project)
                .build();
    }
    
    public static LoanRepaymentReward create(Project project, LoanRepaymentRewardRequest request) {
        return LoanRepaymentReward.builder()
                .project(project)
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .interestRate(request.getInterestRate())
                .loanTermMonths(request.getLoanTermMonths())
                .repaymentFrequency(request.getRepaymentFrequency())
                .gracePeriodMonths(request.getGracePeriodMonths())
                .earlyRepaymentAllowed(request.isEarlyRepaymentAllowed())
                .collateralRequired(request.isCollateralRequired())
                .collateralDescription(request.getCollateralDescription())
                .build();
    }
    
    public void updateLoanRepaymentReward(LoanRepaymentRewardRequest request) {
        this.setTitle(request.getTitle());
        this.setDescription(request.getDescription());
        this.setAmount(request.getAmount());
        this.interestRate = request.getInterestRate();
        this.loanTermMonths = request.getLoanTermMonths();
        this.repaymentFrequency = request.getRepaymentFrequency();
        this.gracePeriodMonths = request.getGracePeriodMonths();
        this.earlyRepaymentAllowed = request.isEarlyRepaymentAllowed();
        this.collateralRequired = request.isCollateralRequired();
        this.collateralDescription = request.getCollateralDescription();
    }
}