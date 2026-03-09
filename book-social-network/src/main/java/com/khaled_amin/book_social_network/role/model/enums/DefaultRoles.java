package com.khaled_amin.book_social_network.role.constants;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum DefaultRoles {
    ADMIN("System administrator"),
    USER("Default system user"),
    MODERATOR("Content moderator");

    private final String description;
}
