package com.example.finnect.entity.enums;

public enum FundingType {
    EQUITY("지분투자"),
    DONATION("후원"),
    P2P_LOAN("P2P 대출");

    private final String description;

    FundingType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
