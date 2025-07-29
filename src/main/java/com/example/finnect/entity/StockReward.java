package com.example.finnect.entity;


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
import com.example.finnect.dto.request.StockRewardRequest;

@Entity
@Table(name = "stock_rewards")
@DiscriminatorValue("STOCK") // 상속 구조에서 내 타입을 식별하는 값은 'STOCK'
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder // 부모 클래스와 함께 빌더 패턴 사용할 수 있음
public class StockReward extends Reward {
    // 주식 기본 정보
    @Column(name = "stock_type", length = 100, nullable = false)
    private String stockType; // 주식종류 (예: 우선주(전환상환))

    @Column(name = "target_amount", precision = 15, scale = 0, nullable = false)
    private BigDecimal targetAmount; // 목표금액 (예: 200,000,000원)

    @Column(name = "share_price", precision = 15, scale = 0, nullable = false)
    private BigDecimal sharePrice; // 1주당 발행가 (예: 10,000원)

    @Column(name = "min_investment", precision = 15, scale = 0, nullable = false)
    private BigDecimal minInvestment; // 최소투자금액 (예: 500,000원)

    // 주식 발행 정보
    @Column(name = "min_investment_shares", nullable = false)
    private Integer minInvestmentShares; // 최소투자주식수 (예: 50주)

    @Column(name = "total_shares_to_issue", nullable = false)
    private Long totalSharesToIssue; // 신규발행예정주식수 (예: 20,000주)

    @Column(name = "pre_money_valuation", precision = 15, scale = 0)
    private BigDecimal preMoneyValuation; // 기업가치평가액 (예: 389억원 pre-money)

    @Column(name = "face_value", precision = 10, scale = 0, nullable = false)
    private BigDecimal faceValue; // 1주당 액면가 (예: 500원)

    // 날짜 관련
    @Column(name = "subscription_start_date", nullable = false)
    private LocalDate subscriptionStartDate; // 청약개시일 (예: 2025-06-27)

    @Column(name = "subscription_end_date", nullable = false)
    private LocalDate subscriptionEndDate; // 청약종료일 (예: 2025-08-07)

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate; // 납입일 (예: 2025-08-20)

    @Column(name = "issue_date", nullable = false)
    private LocalDate issueDate; // 발행일 (예: 2025-08-21)

    @Column(name = "delivery_date", nullable = false)
    private LocalDate deliveryDate; // 교부일 (예: 2025-09-22)

    public static StockReward createDraft(Project project) {
        return StockReward.builder()
                .project(project)
                .build();
    }
    
    public static StockReward create(Project project, StockRewardRequest request) {
        return StockReward.builder()
                .project(project)
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .stockType(request.getStockType())
                .targetAmount(request.getTargetAmount())
                .sharePrice(request.getSharePrice())
                .minInvestment(request.getMinInvestment())
                .minInvestmentShares(request.getMinInvestmentShares())
                .totalSharesToIssue(request.getTotalSharesToIssue())
                .preMoneyValuation(request.getPreMoneyValuation())
                .faceValue(request.getFaceValue())
                .subscriptionStartDate(request.getSubscriptionStartDate())
                .subscriptionEndDate(request.getSubscriptionEndDate())
                .paymentDate(request.getPaymentDate())
                .issueDate(request.getIssueDate())
                .deliveryDate(request.getDeliveryDate())
                .build();
    }
    
    public void updateStockReward(StockRewardRequest request) {
        this.setTitle(request.getTitle());
        this.setDescription(request.getDescription());
        this.setAmount(request.getAmount());
        this.stockType = request.getStockType();
        this.targetAmount = request.getTargetAmount();
        this.sharePrice = request.getSharePrice();
        this.minInvestment = request.getMinInvestment();
        this.minInvestmentShares = request.getMinInvestmentShares();
        this.totalSharesToIssue = request.getTotalSharesToIssue();
        this.preMoneyValuation = request.getPreMoneyValuation();
        this.faceValue = request.getFaceValue();
        this.subscriptionStartDate = request.getSubscriptionStartDate();
        this.subscriptionEndDate = request.getSubscriptionEndDate();
        this.paymentDate = request.getPaymentDate();
        this.issueDate = request.getIssueDate();
        this.deliveryDate = request.getDeliveryDate();
    }
}
