package com.example.finnect.dto.request;

import com.example.finnect.entity.enums.FundingType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Data
public class ProjectCreateRequest {
    
    // 임시저장에서는 모든 필드가 선택사항
    private String title;
    private String content;
    private FundingType fundingType;
    private List<String> projectImages;
    private BigDecimal targetAmount;
    private BigDecimal minInvestment;
    private BigDecimal maxInvestment;
    private LocalDate fundingStartDate;
    private LocalDate fundingEndDate;
    private String cropType;
    private String farmLocation;
    private BigDecimal farmSize;
    private LocalDate expectedHarvestDate;
}