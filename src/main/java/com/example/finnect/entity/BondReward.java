package com.example.finnect.entity;

import com.example.finnect.dto.request.BondRewardRequest;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "bond_rewards")
@DiscriminatorValue("BOND")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class BondReward extends Reward {

    // 채권 기본 정보
    @Column(name = "bond_type", length = 100, nullable = false)
    private String bondType; // 채권명 (예: 이표채)

    @Column(name = "target_amount", precision = 15, scale = 0, nullable = false)
    private BigDecimal targetAmount; // 목표금액 (예: 100,000,000원)

    @Column(name = "interest_rate", precision = 5, scale = 2, nullable = false)
    private BigDecimal interestRate; // 이자율 (예: 연 5%)

    @Column(name = "min_investment", precision = 15, scale = 0, nullable = false)
    private BigDecimal minInvestment; // 최소투자금액 (예: 1,000,000원)

    // 기간 관련
    @Column(name = "interest_payment_cycle", nullable = false)
    private Integer interestPaymentCycle; // 이자지급주기 (개월, 예: 12개월)

    @Column(name = "maturity_period", nullable = false)
    private Integer maturityPeriod; // 만기 (개월, 예: 36개월)

    // 날짜 관련
    @Column(name = "maturity_date", nullable = false)
    private LocalDate maturityDate; // 만기일 (예: 2028-08-14)

    @Column(name = "subscription_start_date", nullable = false)
    private LocalDate subscriptionStartDate; // 청약개시일 (예: 2025-07-17)

    @Column(name = "subscription_end_date", nullable = false)
    private LocalDate subscriptionEndDate; // 청약종료일 (예: 2025-08-04)

    @Column(name = "announcement_date", nullable = false)
    private LocalDate announcementDate; // 납입일 (예: 2025-08-14)

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate; // 발행일 (예: 2025-08-14)

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate; // 교부일 (예: 2025-08-14)

    public static BondReward createDraft(Project project) {
        return BondReward.builder()
                .project(project)
                .build();
    }
    
    public static BondReward create(Project project, BondRewardRequest request) {
        return BondReward.builder()
                .project(project)
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .bondType(request.getBondType())
                .targetAmount(request.getTargetAmount())
                .interestRate(request.getInterestRate())
                .minInvestment(request.getMinInvestment())
                .interestPaymentCycle(request.getInterestPaymentCycle())
                .maturityPeriod(request.getMaturityPeriod())
                .maturityDate(request.getMaturityDate())
                .subscriptionStartDate(request.getSubscriptionStartDate())
                .subscriptionEndDate(request.getSubscriptionEndDate())
                .announcementDate(request.getAnnouncementDate())
                .issueDate(request.getIssueDate())
                .paymentDate(request.getPaymentDate())
                .build();
    }
    
    public void updateBondReward(BondRewardRequest request) {
        this.setTitle(request.getTitle());
        this.setDescription(request.getDescription());
        this.setAmount(request.getAmount());
        this.bondType = request.getBondType();
        this.targetAmount = request.getTargetAmount();
        this.interestRate = request.getInterestRate();
        this.minInvestment = request.getMinInvestment();
        this.interestPaymentCycle = request.getInterestPaymentCycle();
        this.maturityPeriod = request.getMaturityPeriod();
        this.maturityDate = request.getMaturityDate();
        this.subscriptionStartDate = request.getSubscriptionStartDate();
        this.subscriptionEndDate = request.getSubscriptionEndDate();
        this.announcementDate = request.getAnnouncementDate();
        this.issueDate = request.getIssueDate();
        this.paymentDate = request.getPaymentDate();
    }
}
