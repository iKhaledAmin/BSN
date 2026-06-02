package com.khaled_amin.book_social_network.identity.capability.domain.value;


import com.khaled_amin.book_social_network.identity.capability.exception.CapabilityValidationException;

public record CapabilityName(String value) {

    public static final int MAX_LENGTH = 100;

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
            throw CapabilityValidationException.invalidName()
                    .withClientDetails("reason", "Capability name must not be null or empty");
        }

        if (value.length() > MAX_LENGTH) {
            throw CapabilityValidationException.invalidName()
                    .withClientDetails("reason", "Capability name exceeds maximum allowed length")
                    .withClientDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }

        if (!value.matches(PATTERN)) {
            throw CapabilityValidationException.invalidName()
                    .withClientDetails("reason", "Capability name must contain only letters and spaces")
                    .withClientDetails("expectedFormat", "letters_and_spaces_only")
                    .withDebugDetails("receivedValue", value)
                    .withDebugDetails("pattern", PATTERN);
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