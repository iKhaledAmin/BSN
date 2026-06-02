package com.khaled_amin.book_social_network.identity.capability.domain.value;

import com.khaled_amin.book_social_network.identity.capability.exception.CapabilityValidationException;

public record CapabilityAction(String value) {

    public static final int MAX_LENGTH = 100;

    /**
     * Canonical authorization action format.
     * <p>
     * Examples:
     * <li> create
     * <li> read
     * <li> update
     * <li> delete
     * <li> approve
     * <li> reset_password
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
            throw CapabilityValidationException.invalidAction()
                    .withDebugDetails("reason", "Capability action must not be null or empty")
                    .withDebugDetails("receivedValue", value);
        }

        if (value.length() > MAX_LENGTH) {
            throw CapabilityValidationException.invalidAction()
                    .withDebugDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }

        if (!value.matches(PATTERN)) {
            throw CapabilityValidationException.invalidAction()
                    .withDebugDetails("expectedFormat", "lowercase_with_underscores")
                    .withDebugDetails("receivedValue", value)
                    .withDebugDetails("pattern", PATTERN);
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