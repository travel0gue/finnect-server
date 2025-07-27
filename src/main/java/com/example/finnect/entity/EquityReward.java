package com.example.finnect.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;

@Entity
@Table(name = "equity_rewards")
@DiscriminatorValue("EQUITY")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class EquityReward extends Reward {
    
    @Column(name = "equity_percentage", precision = 5, scale = 2)
    private BigDecimal equityPercentage;
    
    @Column(name = "share_class", length = 50)
    private String shareClass;
    
    @Column(name = "voting_rights")
    private boolean votingRights;
    
    @Column(name = "dividend_rights")
    private boolean dividendRights;
    
    @Column(name = "min_holding_period")
    private Integer minHoldingPeriodMonths;
    
    @Column(name = "expected_return", precision = 5, scale = 2)
    private BigDecimal expectedReturnPercentage;
}