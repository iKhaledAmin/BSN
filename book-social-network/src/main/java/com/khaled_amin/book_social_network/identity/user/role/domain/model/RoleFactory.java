package com.khaled_amin.book_social_network.identity.user.role.domain.model;

import com.khaled_amin.book_social_network.identity.user.role.domain.command.CreateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.exception.RoleDomainException;
import org.springframework.stereotype.Component;

@Component
public class RoleFactory {

    public Role createBusinessRole(CreateRoleCommand command) {
        if (command == null) {
            throw RoleDomainException
                    .invalidCommand()
                    .withDetail("reason", "CreateRoleCommand object must not be null");
        }

        return Role.create(
                command.name(),
                command.displayName(),
                command.description(),
                RoleType.BUSINESS,
                command.defaultRole(),
                command.protectedRole()
        );
    }
    public Role createSystemRole(SystemRole systemRole) {
        if (systemRole == null) {
            throw RoleDomainException
                    .invalidSystemRole()
                    .withDetail("reason", "SystemRole object must not be null");
        }

        return Role.create(
                systemRole.getName(),
                systemRole.getDisplayName(),
                systemRole.getDescription(),
                RoleType.SYSTEM,
                systemRole.isDefaultRole(),
                true
        );
    }

}