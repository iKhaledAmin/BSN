package com.khaled_amin.book_social_network.identity.user.role.domain.command;

import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDescription;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDisplayName;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;

public record CreateRoleCommand(
        RoleName name,
        RoleDisplayName displayName,
        RoleDescription description,
        boolean defaultRole,
        boolean protectedRole
) {

    public static CreateRoleCommand of(
        String name,
        String displayName,
        String description,
        boolean defaultRole,
        boolean protectedRole
    ){
        return new CreateRoleCommand(
                RoleName.of(name),
                RoleDisplayName.of(displayName),
                RoleDescription.of(description),
                defaultRole,
                protectedRole
        );

    }


}