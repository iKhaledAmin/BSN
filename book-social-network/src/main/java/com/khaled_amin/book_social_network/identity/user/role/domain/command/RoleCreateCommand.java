package com.khaled_amin.book_social_network.identity.user.role.domain.command;

import com.khaled_amin.book_social_network.identity.user.role.domain.model.RoleType;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDescription;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDisplayName;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;

public record RoleCreateCommand(
        RoleName name,
        RoleDisplayName displayName,
        RoleDescription description,
        boolean defaultRole,
        boolean protectedRole,
        RoleType roleType
) {

    public static RoleCreateCommand of(
        String name,
        String displayName,
        String description,
        boolean defaultRole,
        boolean protectedRole,
        RoleType roleType
    ){
        return new RoleCreateCommand(
                RoleName.of(name),
                RoleDisplayName.of(displayName),
                RoleDescription.of(description),
                defaultRole,
                protectedRole,
                roleType
        );

    }


}