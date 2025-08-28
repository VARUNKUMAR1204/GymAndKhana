package com.gymandkhana.user.model;

public enum MembershipType {
    BASIC(799.0),
    PREMIUM(1299.0),
    VIP(1999.0);

    private final Double defaultPrice;

    MembershipType(Double defaultPrice) {
        this.defaultPrice = defaultPrice;
    }

    public Double getDefaultPrice() {
        return defaultPrice;
    }
}