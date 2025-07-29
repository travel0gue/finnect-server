package com.example.finnect.dto.response;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class DonationRewardResponse extends RewardResponse {
    private Long id;
    private String title;
    private String description;
    private BigDecimal amount;
    private String rewardType;
    private String rewardItem;
    private BigDecimal price;
    private LocalDate estimatedDeliveryDate;
    private Integer limitedQuantity;
    private Integer remainingQuantity;
    private boolean shippingRequired;
    private BigDecimal shippingCost;
}
