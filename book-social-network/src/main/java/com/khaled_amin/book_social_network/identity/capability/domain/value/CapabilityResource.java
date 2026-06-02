package com.khaled_amin.book_social_network.identity.capability.domain.value;

import com.khaled_amin.book_social_network.identity.capability.exception.CapabilityValidationException;

public record CapabilityResource(String value) {

    public static final int MAX_LENGTH = 100;

    /**
     * Canonical authorization resource format.
     * <p>
     * Examples:
     *  <li>  role
     *  <li>  capability
     *  <li>  stock_item
     *  <li>  customer_order
     *  <li>  password_reset
     *
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
            throw CapabilityValidationException.invalidResource()
                    .withDebugDetails("reason", "Capability resource must not be null or empty")
                    .withDebugDetails("receivedValue", value);
        }

        if (value.length() > MAX_LENGTH) {
            throw CapabilityValidationException.invalidResource()
                    .withDebugDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }

        if (!value.matches(PATTERN)) {
            throw CapabilityValidationException.invalidResource()
                    .withDebugDetails("expectedFormat", "lowercase_with_underscores")
                    .withDebugDetails("receivedValue", value)
                    .withDebugDetails("pattern", PATTERN);
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