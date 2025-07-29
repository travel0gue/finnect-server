package com.example.finnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;
import com.example.finnect.dto.request.DonationRewardRequest;

@Entity
@Table(name = "donation_rewards")
@DiscriminatorValue("DONATION")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DonationReward extends Reward {
    
    @Column(name = "reward_item")
    private String rewardItem;

    @Column(name = "price")
    private BigDecimal price;
    
    @Column(name = "estimated_delivery_date")
    private LocalDate estimatedDeliveryDate;
    
    @Column(name = "limited_quantity")
    private Integer limitedQuantity;
    
    @Column(name = "remaining_quantity")
    private Integer remainingQuantity;
    
    @Column(name = "shipping_required")
    private boolean shippingRequired = true;
    
    @Column(name = "shipping_cost", precision = 10, scale = 2)
    private BigDecimal shippingCost;

    public static DonationReward createDraft(Project project) {
        return DonationReward.builder()
                .project(project)
                .build();
    }
    
    public static DonationReward create(Project project, DonationRewardRequest request) {
        return DonationReward.builder()
                .project(project)
                .title(request.getTitle())
                .description(request.getDescription())
                .amount(request.getAmount())
                .rewardItem(request.getRewardItem())
                .price(request.getPrice())
                .estimatedDeliveryDate(request.getEstimatedDeliveryDate())
                .limitedQuantity(request.getLimitedQuantity())
                .remainingQuantity(request.getRemainingQuantity())
                .shippingRequired(request.isShippingRequired())
                .shippingCost(request.getShippingCost())
                .build();
    }
    
    public void updateDonationReward(DonationRewardRequest request) {
        this.setTitle(request.getTitle());
        this.setDescription(request.getDescription());
        this.setAmount(request.getAmount());
        this.rewardItem = request.getRewardItem();
        this.price = request.getPrice();
        this.estimatedDeliveryDate = request.getEstimatedDeliveryDate();
        this.limitedQuantity = request.getLimitedQuantity();
        this.remainingQuantity = request.getRemainingQuantity();
        this.shippingRequired = request.isShippingRequired();
        this.shippingCost = request.getShippingCost();
    }
}