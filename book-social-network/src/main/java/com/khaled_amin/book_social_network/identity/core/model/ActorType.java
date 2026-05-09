package com.khaled_amin.book_social_network.identity.core.model;

public enum ActorType {
    ACCOUNT,
    SYSTEM,
    CLIENT,
    ANONYMOUS;


    /**
     * Converts the given value to {@link ActorType}.
     *
     * <p>Performs strict matching using {@link Enum#valueOf(Class, String)}.
     * Callers should handle and translate exceptions if the input is external.</p>
     *
     * @param value {@link String} raw actor type value
     * @return corresponding {@link ActorType}
     * @throws IllegalArgumentException if value is null, blank, or unsupported
     */
    public static ActorType from(String value) {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ActorType value is missing");
        }

        try {
            return ActorType.valueOf(value);
        } catch (IllegalArgumentException ex) {
            throw new IllegalArgumentException("Invalid ActorType value: " + value, ex);
        }
    }
}