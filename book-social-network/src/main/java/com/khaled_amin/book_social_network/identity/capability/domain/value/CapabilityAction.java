package com.khaled_amin.book_social_network.identity.capability.domain.value;

import com.khaled_amin.book_social_network.identity.capability.domain.exception.CapabilityDomainException;

public record CapabilityAction(String value) {

    public static final int MAX_LENGTH = 50;

    /**
     * Canonical authorization action format.
     *
     * Examples:
     * - create
     * - read
     * - update
     * - delete
     * - approve
     * - reset_password
     */
    public static final String PATTERN = "^[a-z]+(?:_[a-z]+)*$";

    public CapabilityAction {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim().toLowerCase();
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw CapabilityDomainException.invalidCapability()
                    .withDetail("reason", "Capability action must not be null or empty");
        }

        if (!value.matches(PATTERN)) {
            throw CapabilityDomainException.invalidCapability()
                    .withDetail("value", value)
                    .withDetail("reason", "Capability action must contain only lowercase letters and underscores");
        }

        if (value.length() > MAX_LENGTH) {
            throw CapabilityDomainException.invalidCapability()
                    .withDetail("reason", "Capability action too long")
                    .withDetail("maxLength", MAX_LENGTH);
        }
    }

    public static CapabilityAction of(String value) {
        return new CapabilityAction(value);
    }

    @Override
    public String toString() {
        return value;
    }
}