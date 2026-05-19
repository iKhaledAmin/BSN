package com.khaled_amin.book_social_network.identity.capability.domain.value;

import com.khaled_amin.book_social_network.identity.capability.domain.exception.CapabilityDomainException;


public record CapabilityName(String value) {

    public static final int MAX_LENGTH = 150;

    public static final String PATTERN = "^[A-Za-z]+(?: [A-Za-z]+)*$";

    public CapabilityName {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim().replaceAll("\\s+", " ");
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw CapabilityDomainException.invalidCapability()
                    .withDetail("reason", "Capability name must not be null or empty");
        }

        if (!value.matches(PATTERN)) {
            throw CapabilityDomainException.invalidCapability()
                    .withDetail("value", value)
                    .withDetail("reason", "Name must contain only letters and spaces");
        }

        if (value.length() > MAX_LENGTH) {
            throw CapabilityDomainException.invalidCapability()
                    .withDetail("reason", "Capability name too long")
                    .withDetail("maxLength", MAX_LENGTH);
        }
    }

    public static CapabilityName of(String value) {
        return new CapabilityName(value);
    }

    @Override
    public String toString() {
        return value;
    }
}