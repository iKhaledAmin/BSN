package com.khaled_amin.book_social_network.identity.user.role.domain.model;

import com.khaled_amin.book_social_network.identity.user.role.domain.command.RoleCreateCommand;
import com.khaled_amin.book_social_network.identity.user.role.exception.RoleTechnicalException;
import org.springframework.stereotype.Component;

@Component
public class RoleFactory {

    public Role createBusinessRole(
            String name,
            String displayName,
            String description,
            boolean defaultRole,
            boolean protectedRole
            ) {

        RoleCreateCommand command = RoleCreateCommand.of(
                name,
                displayName,
                description,
                defaultRole,
                protectedRole,
                RoleType.BUSINESS
        );

        return Role.create(command);
    }
    public Role createSystemRole(SystemRole systemRole) {
        if (systemRole == null) {
            throw RoleTechnicalException.nullSystemRole();
        }

        RoleCreateCommand command = RoleCreateCommand.of(
                systemRole.getName().toString(),
                systemRole.getDisplayName().toString(),
                systemRole.getDescription().toString(),
                systemRole.isDefaultRole(),
                true,
                RoleType.SYSTEM
        );


        return Role.create(command);
    }

}