package com.khaled_amin.book_social_network.identity.user.role.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RoleType {

    SYSTEM("System-defined role managed internally by the application"),
    BUSINESS("Business-defined role created dynamically by administrators");

    private final String description;

    public boolean isSystem() {
        return this == SYSTEM;
    }

    public boolean isBusiness() {
        return this == BUSINESS;
    }
}