package com.khaled_amin.book_social_network.role.application.validation;

import com.khaled_amin.book_social_network.role.application.exception.RoleApplicationException;
import com.khaled_amin.book_social_network.role.application.service.RoleUsageService;
import com.khaled_amin.book_social_network.role.domain.command.CreateRoleCommand;
import com.khaled_amin.book_social_network.role.domain.command.UpdateRoleCommand;
import com.khaled_amin.book_social_network.role.domain.model.Role;
import com.khaled_amin.book_social_network.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.role.domain.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleApplicationValidator {

    private final RoleRepository roleRepository;
    private final RoleUsageService roleUsageService;

    public void validateCreate(CreateRoleCommand command) {
        ensureNameUnique(command.name().value());
        ensureDisplayNameUnique(command.displayName().value());
    }

    public void validateCreate(SystemRole systemRole) {
        ensureNameUnique(systemRole.getName().value());
        ensureDisplayNameUnique(systemRole.getDisplayName().value());
    }

    public void validateUpdate(Role existing, UpdateRoleCommand command) {

        if (command.displayName() != null &&
                !existing.getDisplayName().equals(command.displayName().value())) {
            ensureDisplayNameUnique(command.displayName().value());
        }
    }

    public void validateDelete(Long roleId) {
        ensureRoleNotInUse(roleId);
    }


    // ------------------------------------- PRIVATE METHODS ------------------------------------- //

    private void ensureNameUnique(String name) {
        if (roleRepository.existsByName(name)) {
            throw RoleApplicationException
                    .alreadyExists()
                    .withDetail("reason", "Role name already exist");
        }
    }

    private void ensureDisplayNameUnique(String displayName) {
        if (roleRepository.existsByDisplayName(displayName)) {
            throw RoleApplicationException
                    .alreadyExists()
                    .withDetail("reason", "Role display name already exist");
        }
    }

    private void ensureRoleNotInUse(Long roleId){
        if (roleUsageService.isAssignedToAnyAccount(roleId)) {
            throw RoleApplicationException
                    .deletionViolation()
                    .withDetail("reason", "This role is assigned to one or more accounts");
        }
    }

    // ------------------------------------- PRIVATE METHODS ------------------------------------- //
}