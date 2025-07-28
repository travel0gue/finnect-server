package com.example.finnect.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "investments")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Investment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reward_id", nullable = false)
    private Reward reward;

    @Column(name = "amount", nullable = false, precision = 15, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "investment_status", nullable = false)
    private InvestmentStatus status;

    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Setter
    @OneToOne(mappedBy = "investment", cascade = CascadeType.ALL, orphanRemoval = true)
    private Delivery delivery;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // 투자 상태 enum
    public enum InvestmentStatus {
        PENDING, COMPLETED, CANCELLED, REFUNDED
    }

    // 연관된 프로젝트를 얻기 위한 편의 메소드
    public Project getProject() {
        return reward.getProject();
    }

    // 리워드 타입 확인을 위한 편의 메소드들
    public boolean isDonationReward() {
        return reward instanceof DonationReward;
    }

    public boolean isStockReward() {
        return reward instanceof StockReward;
    }

    public boolean isBondReward() { return reward instanceof BondReward; }

    public boolean isLoanRepaymentReward() {
        return reward instanceof LoanRepaymentReward;
    }

    // 타입별 리워드 getter 메소드들
    public DonationReward getDonationReward() {
        if (isDonationReward()) {
            return (DonationReward) reward;
        }
        return null;
    }

    public StockReward getStockReward() {
        if (isStockReward()) {
            return (StockReward) reward;
        }
        return null;
    }

    public BondReward getBondReward() {
        if (isBondReward()) {
            return (BondReward) reward;
        }
        return null;
    }

    public LoanRepaymentReward getLoanRepaymentReward() {
        if (isLoanRepaymentReward()) {
            return (LoanRepaymentReward) reward;
        }
        return null;
    }

    // 양방향 매핑 편의 메소드
    public void setUser(User user) {
        this.user = user;
    }

    public void setReward(Reward reward) {
        this.reward = reward;
    }
}
