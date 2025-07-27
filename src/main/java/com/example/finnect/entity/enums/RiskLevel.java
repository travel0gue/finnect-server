package com.example.finnect.entity.enums;

public enum RiskLevel {
    LOW("낮음"),
    MEDIUM("보통"),
    HIGH("높음"),
    VERY_HIGH("매우높음");

    private final String description;

    RiskLevel(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
