package com.khaled_amin.book_social_network.role.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.Optional;


@Getter
@RequiredArgsConstructor
public enum SystemRoles {

    ADMIN("ADMIN","SYSTEM_ADMIN","System administrator", false),
    USER("USER","SYSTEM_USER","Default system user", true),
    MODERATOR("MODERATOR","SYSTEM_MODERATOR","Content moderator", false);

    private final String name;
    private final String systemCode;
    private final String description;
    private final boolean defaultRole;


    public static Optional<SystemRoles> getBySystemCode(String systemCode) {
    return Arrays.stream(SystemRoles.values())
            .filter(r -> r.getSystemCode().equals(systemCode))
            .findFirst();
    }

}




