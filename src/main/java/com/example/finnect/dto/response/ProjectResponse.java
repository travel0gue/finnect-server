package com.example.finnect.dto.response;

import com.example.finnect.entity.*;
import com.example.finnect.entity.enums.FundingType;
import com.example.finnect.entity.enums.ProjectStatus;
import com.example.finnect.entity.enums.RiskLevel;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class ProjectResponse {
    
    private Long id;
    private Long userId;
    private FundingType fundingType;
    private ProjectStatus status;
    private String title;
    private String content;
    private List<String> projectImages;
    private BigDecimal targetAmount;
    private BigDecimal currentAmount;
    private BigDecimal minInvestment;
    private BigDecimal maxInvestment;
    private LocalDate fundingStartDate;
    private LocalDate fundingEndDate;
    private String cropType;
    private String farmLocation;
    private BigDecimal farmSize;
    private LocalDate expectedHarvestDate;
    private RiskLevel riskLevel;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private BigDecimal fundingProgressPercentage;
    private boolean isFundingActive;
    private long daysUntilDeadline;
    
    public static ProjectResponse from(Project project) {

        return ProjectResponse.builder()
                .id(project.getId())
                .userId(project.getUserId())
                .fundingType(project.getFundingType())
                .status(project.getStatus())
                .title(project.getTitle())
                .content(project.getContent())
                .projectImages(project.getProjectImages())
                .targetAmount(project.getTargetAmount())
                .currentAmount(project.getCurrentAmount())
                .minInvestment(project.getMinInvestment())
                .maxInvestment(project.getMaxInvestment())
                .fundingStartDate(project.getFundingStartDate())
                .fundingEndDate(project.getFundingEndDate())
                .cropType(project.getCropType())
                .farmLocation(project.getFarmLocation())
                .farmSize(project.getFarmSize())
                .expectedHarvestDate(project.getExpectedHarvestDate())
                .riskLevel(project.getRiskLevel())
                .createdAt(project.getCreatedAt())
                .updatedAt(project.getUpdatedAt())
                .fundingProgressPercentage(project.getFundingProgressPercentage())
                .isFundingActive(project.isFundingActive())
                .daysUntilDeadline(project.getDaysUntilDeadline())
                .build();
    }
}
