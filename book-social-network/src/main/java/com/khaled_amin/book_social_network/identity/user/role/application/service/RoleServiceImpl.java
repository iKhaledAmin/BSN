package com.khaled_amin.book_social_network.identity.user.role.application.service;

import com.khaled_amin.book_social_network.core.servise.EntityRetrievalService;
import com.khaled_amin.book_social_network.identity.capability.application.port.CapabilityService;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.core.provider.ActorProvider;
import com.khaled_amin.book_social_network.identity.user.role.application.exception.RoleApplicationException;
import com.khaled_amin.book_social_network.identity.user.role.application.policy.RolePolicyContextFactory;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.CreateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.UpdateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.RoleFactory;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.application.policy.RolePolicyEngine;
import com.khaled_amin.book_social_network.identity.user.role.application.validation.RoleApplicationValidator;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.user.role.domain.repository.RoleRepository;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleId;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleFactory roleFactory;
    private final RolePolicyEngine rolePolicyEngine;
    private final ActorProvider actorProvider;
    private final CapabilityService capabilityService;
    private final EntityRetrievalService entityRetrievalService;
    private final RoleApplicationValidator roleApplicationValidator;
    private final RolePolicyContextFactory policyContextFactory;

    public static final String DEFAULT_ROLES_CACHE = "defaultRoles";


    @Transactional
    @CacheEvict(value = DEFAULT_ROLES_CACHE, allEntries = true)
    @Override
    public Role createBusinessRole(CreateRoleCommand command) {
        if (command == null) {
            throw RoleApplicationException
                    .invalidCommand()
                    .withDetail("reason", "Create command must not be null");
        }

        Actor actor = actorProvider.getCurrent();

        // Application validation
        roleApplicationValidator.validateCreate(command);

        // Policy validation
        rolePolicyEngine.canCreateBusinessRole(
                policyContextFactory.forCreateBusinessRole(actor, command)
        );

        // Domain logic
        Role role = roleFactory.createBusinessRole(command);

        // Persistence
        return roleRepository.save(role);
    }

    @Transactional
    @CacheEvict(value = DEFAULT_ROLES_CACHE, allEntries = true)
    @Override
    public Role createSystemRole(SystemRole systemRole){

        if (systemRole == null){
            throw RoleApplicationException
                    .invalidSystemRole()
                    .withDetail("reason", "System role must not be null");
        }

        Actor actor = actorProvider.getCurrent();

        // Application validation
        roleApplicationValidator.validateCreate(systemRole);

        // Policy validation
        rolePolicyEngine.canCreateSystemRole(
                policyContextFactory.forCreateSystemRole(actor)
        );

        // Domain logic
        Role role = roleFactory.createSystemRole(systemRole);

        // Persistence
        return roleRepository.save(role);

    }

    @Transactional
    @CacheEvict(value = DEFAULT_ROLES_CACHE, allEntries = true)
    @Override
    public Role update(RoleName roleName, UpdateRoleCommand command) {

        if (command == null) {
            throw RoleApplicationException
                    .invalidCommand()
                    .withDetail("reason", "Update command must not be null");
        }


        Role existingRole = getByName(roleName);
        Actor actor = actorProvider.getCurrent();

        // Application validation
        roleApplicationValidator.validateUpdate(existingRole, command);

        // Policy validation
        rolePolicyEngine.canUpdateRole(
                policyContextFactory.forUpdate(actor, existingRole, command)
        );

        // Domain logic
        existingRole.update(command);

        // Persistence
        return roleRepository.save(existingRole);
    }

    @Transactional
    @Override
    public Role addCapability(RoleName roleName, CapabilityCode code) {

//        if (roleName == null) {
//            throw .invalidRole()
//                    .withDetail("reason", "Role name must not be null");
//        }
//
//        if (code == null) {
//            throw .invalidCapability()
//                    .withDetail("reason", "Capability code must not be null");
//        }

        Role role = getByName(roleName);

        Capability capability = capabilityService.getByCode(code);

        Actor actor = actorProvider.getCurrent();

        /*
         * POLICY VALIDATION
         *
         * Add later:
         *
         * rolePolicyEngine.canAssignCapability(...)
         */

        role.addCapability(capability);

        return roleRepository.save(role);
    }


    @Transactional
    @Override
    public Role removeCapability(RoleName roleName, CapabilityCode code) {

//        if (roleName == null) {
//            throw .invalidRole()
//                    .withDetail("reason", "Role name must not be null");
//        }
//
//        if (code == null) {
//            throw .invalidCapability()
//                    .withDetail("reason", "Capability code must not be null");
//        }

        Role role = getByName(roleName);

        Capability capability = capabilityService.getByCode(code);

        Actor actor = actorProvider.getCurrent();

        /*
         * POLICY VALIDATION
         *
         * Add later:
         *
         * rolePolicyEngine.canRemoveCapability(...)
         */

        role.removeCapability(capability);

        return roleRepository.save(role);
    }

    @Transactional
    @CacheEvict(value = DEFAULT_ROLES_CACHE, allEntries = true)
    @Override
    public void delete(RoleName roleName) {

        Role role = getByName(roleName);
        Actor actor = actorProvider.getCurrent();

        // Application validation
        roleApplicationValidator.validateDelete(role.getId());

        // Policy validation
        rolePolicyEngine.canDeleteRole(
                policyContextFactory.forDelete(actor, role)
        );

        // Persistence
        roleRepository.delete(role);
    }

    // ----------------------------------------- Retrieval methods ----------------------------------------- //

    @Cacheable(DEFAULT_ROLES_CACHE)
    @Override
    public List<Role> getDefaultRoles() {

        List<Role> roles = roleRepository.findDefaultRoles();

        if (roles.isEmpty()) {
            throw RoleApplicationException.defaultRoleNotConfigured();
        }

        return roles;
    }


    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getOptionalById(Long roleId) {
        return entityRetrievalService.getOptionalById(Role.class, roleId);
    }

    @Override
    public Role getById(Long roleId) {
        return entityRetrievalService.getById(Role.class, roleId, RoleApplicationException::notFound);
    }

    @Override
    public Role getById(RoleId roleId) {
        return getById(roleId.value());
    }

    @Override
    public Optional<Role> getOptionalByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public Role getByName(String roleName) {
        return getOptionalByName(roleName).orElseThrow(() -> RoleApplicationException.notFound()
                .withDetail("reason", "Role not found for given name")
                .withDetail("roleName",roleName)
        );
    }

    @Override
    public Role getByName(RoleName roleName) {
        return getByName(roleName.value());
    }

    @Override
    public List<Role> getAllByNames(List<RoleName> roleNames) {

        if (roleNames == null || roleNames.isEmpty())
            throw RoleApplicationException.invalidRoles()
                    .withDetail("reason", "Role names list must not be null or empty");

        List<String> names = roleNames.stream()
                .map(RoleName::value)
                .toList();

        List<Role> roles = roleRepository.findAllByNameIn(names);

        if (roles.size() != roleNames.size()) {

            Set<String> foundNames = roles.stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());

            List<String> notFoundNames = names.stream()
                    .filter(name -> !foundNames.contains(name))
                    .toList();

            throw RoleApplicationException.rolesNotFound()
                    .withDetail("requestedRoleNames", names)
                    .withDetail("notFoundRoleNames", notFoundNames);
        }

        return roles;
    }

    // ------------------------------------- End Retrieval methods ----------------------------------------- //


}



