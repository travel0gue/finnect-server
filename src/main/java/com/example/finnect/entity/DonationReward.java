package com.example.finnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

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
    private boolean shippingRequired;
    
    @Column(name = "shipping_cost", precision = 10, scale = 2)
    private BigDecimal shippingCost;
}