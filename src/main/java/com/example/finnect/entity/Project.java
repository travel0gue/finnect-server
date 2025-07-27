package com.example.finnect.entity;

import com.example.finnect.entity.enums.FundingType;
import com.example.finnect.entity.enums.ProjectStatus;
import com.example.finnect.entity.enums.RiskLevel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Entity
@Table(name = "project")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "funding_type")
    private FundingType fundingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus status;

    @Column(name = "title", length = 200)
    private String title;

    @Column(name = "content", columnDefinition = "TEXT")
    private String content;

    @ElementCollection
    @CollectionTable(name = "project_images", joinColumns = @JoinColumn(name = "project_id"))
    @Column(name = "image_url")
    private List<String> projectImages;

    @Column(name = "target_amount", precision = 15, scale = 2)
    private BigDecimal targetAmount;

    @Column(name = "current_amount", precision = 15, scale = 2)
    @Builder.Default
    private BigDecimal currentAmount = BigDecimal.ZERO;

    @Column(name = "min_investment", precision = 10, scale = 2)
    private BigDecimal minInvestment;

    @Column(name = "max_investment", precision = 12, scale = 2)
    private BigDecimal maxInvestment;

    @Column(name = "funding_start_date")
    private LocalDate fundingStartDate;

    @Column(name = "funding_end_date")
    private LocalDate fundingEndDate;

    @Column(name = "crop_type", length = 100)
    private String cropType;

    @Column(name = "farm_location", length = 200)
    private String farmLocation;

    @Column(name = "farm_size", precision = 10, scale = 2)
    private BigDecimal farmSize;

    @Column(name = "expected_harvest_date")
    private LocalDate expectedHarvestDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "risk_level")
    private RiskLevel riskLevel;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 리워드와의 관계 설정
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reward> rewards = new ArrayList<>();

    // 리워드 관련 편의 메소드
    public void addReward(Reward reward) {
        rewards.add(reward);
        reward.setProject(this);
    }

    public void removeReward(Reward reward) {
        rewards.remove(reward);
        reward.setProject(null);
    }

    // 리워드 타입별 조회 메소드들
    public List<DonationReward> getDonationRewards() {
        return rewards.stream()
                .filter(reward -> reward instanceof DonationReward)
                .map(reward -> (DonationReward) reward)
                .collect(Collectors.toList());
    }

    public List<EquityReward> getEquityRewards() {
        return rewards.stream()
                .filter(reward -> reward instanceof EquityReward)
                .map(reward -> (EquityReward) reward)
                .collect(Collectors.toList());
    }

    public List<LoanRepaymentReward> getLoanRepaymentRewards() {
        return rewards.stream()
                .filter(reward -> reward instanceof LoanRepaymentReward)
                .map(reward -> (LoanRepaymentReward) reward)
                .collect(Collectors.toList());
    }

    public boolean isFundingActive() {
        return status == ProjectStatus.ACTIVE &&
                LocalDate.now().isBefore(fundingEndDate.plusDays(1)) &&
                LocalDate.now().isAfter(fundingStartDate.minusDays(1));
    }

    public BigDecimal getFundingProgressPercentage() {
        if (targetAmount == null || targetAmount.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return currentAmount.divide(targetAmount, 4, BigDecimal.ROUND_HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    public boolean isFundingGoalReached() {
        return currentAmount.compareTo(targetAmount) >= 0;
    }

    public long getDaysUntilDeadline() {
        return LocalDate.now().until(fundingEndDate).getDays();
    }
}