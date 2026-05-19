package com.khaled_amin.book_social_network.identity.capability.domain.value;

import com.khaled_amin.book_social_network.identity.capability.domain.exception.CapabilityDomainException;

public record CapabilityResource(String value) {

    public static final int MAX_LENGTH = 100;

    /**
     * Canonical authorization resource format.
     *
     * Examples:
     * - role
     * - capability
     * - stock_item
     * - customer_order
     * - password_reset
     */
    public static final String PATTERN = "^[a-z]+(?:_[a-z]+)*$";

    public CapabilityResource {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim().toLowerCase();
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw CapabilityDomainException.invalidCapability()
                    .withDetail("reason", "Capability resource must not be null or empty");
        }

        if (!value.matches(PATTERN)) {
            throw CapabilityDomainException.invalidCapability()
                    .withDetail("value", value)
                    .withDetail("reason", "Capability resource must contain only lowercase letters and underscores");
        }

        if (value.length() > MAX_LENGTH) {
            throw CapabilityDomainException.invalidCapability()
                    .withDetail("reason", "Capability resource too long")
                    .withDetail("maxLength", MAX_LENGTH);
        }
    }

    public static CapabilityResource of(String value) {
        return new CapabilityResource(value);
    }

    @Override
    public String toString() {
        return value;
    }
}