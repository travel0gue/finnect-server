package com.example.finnect.dto.response;

import com.example.finnect.entity.*;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "rewardType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DonationRewardResponse.class, name = "DONATION"),
        @JsonSubTypes.Type(value = LoanRepaymentRewardResponse.class, name = "LOAN"),
        @JsonSubTypes.Type(value = StockRewardResponse.class, name = "STOCK"),
        @JsonSubTypes.Type(value = BondRewardResponse.class, name = "BOND")
})
public class RewardResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal amount;
    private String rewardType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // 정적 팩토리 메서드
    public static RewardResponse from(Reward reward) {
        if (reward instanceof DonationReward) {
            return fromDonationReward((DonationReward) reward);
        } else if (reward instanceof LoanRepaymentReward) {
            return fromLoanReward((LoanRepaymentReward) reward);
        } else if (reward instanceof StockReward) {
            return fromStockReward((StockReward) reward);
        } else if (reward instanceof BondReward) {
            return fromBondReward((BondReward) reward);
        }

        // 기본 리워드 (알 수 없는 타입인 경우)
        return RewardResponse.builder()
                .id(reward.getId())
                .title(reward.getTitle())
                .description(reward.getDescription())
                .amount(reward.getAmount())
                .rewardType("UNKNOWN")
                .createdAt(reward.getCreatedAt())
                .updatedAt(reward.getUpdatedAt())
                .build();
    }

    private static DonationRewardResponse fromDonationReward(DonationReward reward) {
        return DonationRewardResponse.builder()
                .id(reward.getId())
                .title(reward.getTitle())
                .description(reward.getDescription())
                .amount(reward.getAmount())
                .rewardType("DONATION")
                .createdAt(reward.getCreatedAt())
                .updatedAt(reward.getUpdatedAt())
                .rewardItem(reward.getRewardItem())
                .price(reward.getPrice())
                .estimatedDeliveryDate(reward.getEstimatedDeliveryDate())
                .limitedQuantity(reward.getLimitedQuantity())
                .remainingQuantity(reward.getRemainingQuantity())
                .shippingRequired(reward.isShippingRequired())
                .shippingCost(reward.getShippingCost())
                .build();
    }

    private static LoanRepaymentRewardResponse fromLoanReward(LoanRepaymentReward reward) {
        return LoanRepaymentRewardResponse.builder()
                .id(reward.getId())
                .title(reward.getTitle())
                .description(reward.getDescription())
                .amount(reward.getAmount())
                .rewardType("LOAN")
                .createdAt(reward.getCreatedAt())
                .updatedAt(reward.getUpdatedAt())
                .interestRate(reward.getInterestRate())
                .loanTermMonths(reward.getLoanTermMonths())
                .repaymentFrequency(reward.getRepaymentFrequency())
                .gracePeriodMonths(reward.getGracePeriodMonths())
                .earlyRepaymentAllowed(reward.isEarlyRepaymentAllowed())
                .collateralRequired(reward.isCollateralRequired())
                .collateralDescription(reward.getCollateralDescription())
                .build();
    }

    private static StockRewardResponse fromStockReward(StockReward reward) {
        return StockRewardResponse.builder()
                .id(reward.getId())
                .title(reward.getTitle())
                .description(reward.getDescription())
                .amount(reward.getAmount())
                .rewardType("STOCK")
                .createdAt(reward.getCreatedAt())
                .updatedAt(reward.getUpdatedAt())
                .stockType(reward.getStockType())
                .targetAmount(reward.getTargetAmount())
                .sharePrice(reward.getSharePrice())
                .minInvestment(reward.getMinInvestment())
                .minInvestmentShares(reward.getMinInvestmentShares())
                .totalSharesToIssue(reward.getTotalSharesToIssue())
                .preMoneyValuation(reward.getPreMoneyValuation())
                .faceValue(reward.getFaceValue())
                .subscriptionStartDate(reward.getSubscriptionStartDate())
                .subscriptionEndDate(reward.getSubscriptionEndDate())
                .paymentDate(reward.getPaymentDate())
                .issueDate(reward.getIssueDate())
                .deliveryDate(reward.getDeliveryDate())
                .build();
    }

    private static BondRewardResponse fromBondReward(BondReward reward) {
        return BondRewardResponse.builder()
                .id(reward.getId())
                .title(reward.getTitle())
                .description(reward.getDescription())
                .amount(reward.getAmount())
                .rewardType("BOND")
                .createdAt(reward.getCreatedAt())
                .updatedAt(reward.getUpdatedAt())
                .bondType(reward.getBondType())
                .targetAmount(reward.getTargetAmount())
                .interestRate(reward.getInterestRate())
                .minInvestment(reward.getMinInvestment())
                .interestPaymentCycle(reward.getInterestPaymentCycle())
                .maturityPeriod(reward.getMaturityPeriod())
                .maturityDate(reward.getMaturityDate())
                .subscriptionStartDate(reward.getSubscriptionStartDate())
                .subscriptionEndDate(reward.getSubscriptionEndDate())
                .announcementDate(reward.getAnnouncementDate())
                .issueDate(reward.getIssueDate())
                .paymentDate(reward.getPaymentDate())
                .build();
    }

    // 리스트 변환을 위한 유틸리티 메서드
    public static List<RewardResponse> fromList(List<Reward> rewards) {
        return rewards.stream()
                .map(RewardResponse::from)
                .collect(Collectors.toList());
    }
}
