package com.khaled_amin.book_social_network.identity.user.role.application.service;

import com.khaled_amin.book_social_network.core.servise.EntityRetrievalService;
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
    public Role update(Long roleId, UpdateRoleCommand command) {

        if (command == null) {
            throw RoleApplicationException
                    .invalidCommand()
                    .withDetail("reason", "Update command must not be null");
        }


        Role existingRole = getById(roleId);
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
    @CacheEvict(value = DEFAULT_ROLES_CACHE, allEntries = true)
    @Override
    public void delete(RoleId roleId) {

        Role role = getById(roleId);
        Actor actor = actorProvider.getCurrent();

        // Application validation
        roleApplicationValidator.validateDelete(roleId.value());

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


    @Override
    public List<Role> getAllByIds(List<Long> roleIds) {

        List<Role> roles = roleRepository.findAllById(roleIds);

        if (roles.size() != roleIds.size()) {

            Set<Long> foundIds = roles.stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet());

            List<Long> notFoundIds = roleIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw RoleApplicationException
                    .rolesNotFound()
                    .withDetail("requestedRoleIds", roleIds)
                    .withDetail("notFoundRoleIds", notFoundIds);
        }

        return roles;
    }

    @Override
    public List<Long> getAllDefaultRoleIds() {
        return getDefaultRoles()
                .stream()
                .map(Role::getId)
                .toList();
    }
    // ------------------------------------- End Retrieval methods ----------------------------------------- //


}



