package com.khaled_amin.book_social_network.identity.capability.domain.value;

import com.khaled_amin.book_social_network.identity.capability.domain.exception.CapabilityDomainException;

public record CapabilityCode(String value) {

    private static final int MAX_LENGTH = 100;

    /**
     * Canonical internal capabilities format.
     *
     * Examples:
     * - STOCK_ITEM_READ
     * - STOCK_ITEM_WRITE
     * - USER_CREATE
     * - ORDER_APPROVE
     */
    private static final String PATTERN = "^[A-Z]+(?:_[A-Z]+)*$";

    public CapabilityCode {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw CapabilityDomainException.invalidCapability()
                    .withDetail("reason", "Capability code must not be null or empty");
        }

        if (!value.matches(PATTERN)) {
            throw CapabilityDomainException.invalidCapability()
                    .withDetail("value", value)
                    .withDetail("reason", "Capability code must contain only uppercase letters and underscores");
        }

        if (value.length() > MAX_LENGTH) {
            throw CapabilityDomainException.invalidCapability()
                    .withDetail("reason", "Capability code too long")
                    .withDetail("maxLength", MAX_LENGTH);
        }
    }

    public static CapabilityCode of(String value) {
        return new CapabilityCode(value);
    }

    @Override
    public String toString() {
        return value;
    }
}