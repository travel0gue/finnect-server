package com.example.finnect.dto.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class DonationRewardRequest {
    
    private String title;
    private String description;
    private BigDecimal amount;
    
    private String rewardItem;
    private BigDecimal price;
    private LocalDate estimatedDeliveryDate;
    private Integer limitedQuantity;
    private Integer remainingQuantity;
    private boolean shippingRequired;
    private BigDecimal shippingCost;
}