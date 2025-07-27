package com.example.finnect.entity.enums;

public enum ProjectStatus {
    DRAFT("임시저장"),
    REVIEW("심사중"),
    ACTIVE("펀딩중"),
    FUNDED("펀딩완료"),
    CANCELLED("취소됨"),
    COMPLETED("프로젝트완료"),
    FAILED("펀딩실패");

    private final String description;

    ProjectStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
