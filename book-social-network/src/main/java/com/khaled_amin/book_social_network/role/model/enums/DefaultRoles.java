package com.khaled_amin.book_social_network.role.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DefaultRoles {

    ADMIN("ADMIN", "System administrator"),
    USER("USER", "Default system user"),
    MODERATOR("MODERATOR", "Content moderator");

    private final String name;
    private final String description;
}