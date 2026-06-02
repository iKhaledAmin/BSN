package com.khaled_amin.book_social_network.identity.user.role.application.validation;

import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.SystemActor;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleCreateRequest;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleUpdateRequest;
import com.khaled_amin.book_social_network.identity.user.role.application.service.RoleUsageService;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.user.role.domain.repository.RoleRepository;
import com.khaled_amin.book_social_network.identity.user.role.exception.RoleBusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleValidator {

    private final RoleRepository roleRepository;
    private final RoleUsageService roleUsageService;

    public void ensureCanBeCreate(RoleCreateRequest request) {
        validateNameUniquenessFor(request.getName());
        validateDisplayNameUniquenessFor(request.getDisplayName());
    }

    public void ensureCanBeCreate(SystemRole systemRole) {
        validateNameUniquenessFor(systemRole.getName().toString());
        validateDisplayNameUniquenessFor(systemRole.getDisplayName().toString());

    }

    public void ensureCanBeUpdate(Role existing, RoleUpdateRequest request) {
        String newDisplayName = request.getDisplayName();
        if (newDisplayName != null && !existing.getDisplayName().equals(newDisplayName)) {
            validateDisplayNameUniquenessFor(newDisplayName);
        }
    }

    public void ensureCanBeDelete(Role role) {
        ensureRoleIsDeletable(role);
    }

    public void ensureCaAddCapability(Capability capability , Actor actor){
        if (capability.isSystemManaged() && !(actor instanceof SystemActor)){
            throw RoleBusinessException.systemManagedCapabilityCannotBeAssigned();
        }
    }

    public void ensureCaRemoveCapability(Capability capability , Actor actor){
        if (capability.isSystemManaged() && !(actor instanceof SystemActor)){
            throw RoleBusinessException.systemManagedCapabilityCannotBeRemoved();
        }
    }

    // ------------------------------------- PRIVATE METHODS ------------------------------------- //

    private void validateNameUniquenessFor(String name) {

        if (roleRepository.existsByName(name)) {
            throw RoleBusinessException.nameAlreadyExists()
                    .withClientDetails("roleName", name);
        }
    }

    private void validateDisplayNameUniquenessFor(String displayName) {

        if (roleRepository.existsByDisplayName(displayName)) {
            throw RoleBusinessException.displayNameAlreadyExists()
                    .withClientDetails("displayName", displayName);
        }
    }

    private void ensureRoleIsDeletable(Role role){
        if (roleUsageService.isAssignedToAnyAccount(role.getId())) {
            throw RoleBusinessException.roleAssignedToAccounts()
                    .withDebugDetails("roleName", role.getName());
        }
    }

    // ------------------------------------- PRIVATE METHODS ------------------------------------- //
}