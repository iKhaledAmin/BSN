package com.khaled_amin.book_social_network.identity.user.role.domain.value;

import com.khaled_amin.book_social_network.identity.user.role.domain.exception.RoleDomainException;

public record RoleDisplayName(String value) {
    public RoleDisplayName {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String displayName) {
        return displayName == null ? null : displayName.trim();
    }

    private static void validate(String displayName) {
        if (displayName == null || displayName.isBlank()) {
            throw RoleDomainException
                    .invalidRoleDisplayName()
                    .withDetail("reason", "Role display name must not be null or empty");
        }

        if (!displayName.matches("^[a-zA-Z ]+$")) {
            throw RoleDomainException
                    .invalidRoleDisplayName()
                    .withDetail("value", displayName)
                    .withDetail("reason", "Role display name must contain only letters");
        }

        if (displayName.length() > 50) {
            throw RoleDomainException
                    .invalidRoleDisplayName()
                    .withDetail("reason", "Role display name too long");
        }
    }
    public static RoleDisplayName of(String displayName) {
        return new RoleDisplayName(displayName);
    }

}
