package com.khaled_amin.book_social_network.identity.user.role.domain.value;

import com.khaled_amin.book_social_network.identity.user.role.domain.exception.RoleDomainException;

public record RoleDescription(String value) {
    public RoleDescription {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String description) {
        return description == null ? null : description.trim();
    }

    private static void validate(String description) {
        if (description == null || description.isBlank()) {
            throw RoleDomainException
                    .invalidRoleDescription()
                    .withDetail("reason", "Role description must not be null or empty");
        }

        if (description.length() > 255) {
            throw RoleDomainException
                    .invalidRoleDescription()
                    .withDetail("reason", "Role description too long");
        }
    }
    public static RoleDescription of(String description) {
        return new RoleDescription(description);
    }
}
