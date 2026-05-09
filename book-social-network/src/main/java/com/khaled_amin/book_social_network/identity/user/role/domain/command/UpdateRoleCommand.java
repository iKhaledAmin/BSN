package com.khaled_amin.book_social_network.identity.user.role.domain.command;


import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDescription;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDisplayName;

public record UpdateRoleCommand(
        RoleDisplayName displayName,
        RoleDescription description,
        Boolean defaultRole,
        Boolean protectedRole
) {

    public static UpdateRoleCommand of(
            String displayName,
            String description,
            Boolean defaultRole,
            Boolean protectedRole
    ){
        return new UpdateRoleCommand(
                RoleDisplayName.of(displayName),
                RoleDescription.of(description),
                defaultRole,
                protectedRole
        );
    }
}