package com.khaled_amin.book_social_network.identity.capability.domain.value;

import com.khaled_amin.book_social_network.identity.capability.exception.CapabilityValidationException;

public record CapabilityCode(String value) {

    private static final int MAX_LENGTH = 100;

    /**
     * Canonical internal capabilities format.
     * <p>
     * Examples:
     * <li> STOCK_ITEM_READ
     * <li> USER_CREATE
     * <li> ORDER_APPROVE
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
            throw CapabilityValidationException.invalidCode()
                    .withClientDetails("reason", "Capability code must not be null or empty");
        }

        if (value.length() > MAX_LENGTH) {
            throw CapabilityValidationException.invalidCode()
                    .withDebugDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }

        if (!value.matches(PATTERN)) {
            throw CapabilityValidationException.invalidCode()
                    .withDebugDetails("receivedValue", value)
                    .withDebugDetails("pattern", PATTERN);
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