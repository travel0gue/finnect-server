package com.example.finnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rewards")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "reward_type")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public abstract class Reward {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "amount", precision = 15, scale = 2)
    private BigDecimal amount;
    
    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Investment와의 관계 추가
    @OneToMany(mappedBy = "reward", cascade = CascadeType.ALL)
    private List<Investment> investments = new ArrayList<>();
    
    // 양방향 매핑 편의 메소드
    public void setProject(Project project) {
        this.project = project;
    }
    
    // Investment 추가 편의 메소드
    public void addInvestment(Investment investment) {
        investments.add(investment);
        investment.setReward(this);
    }

    public void removeInvestment(Investment investment) {
        investments.remove(investment);
        investment.setReward(null);
    }
}