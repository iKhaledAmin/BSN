package com.khaled_amin.book_social_network.identity.user.role.application.service;

import com.khaled_amin.book_social_network.core.logging.audit.BusinessEventLogger;
import com.khaled_amin.book_social_network.identity.capability.application.port.CapabilityService;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.core.provider.ActorProvider;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleCreateRequest;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleUpdateRequest;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.RoleUpdateCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.RoleFactory;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.application.validation.RoleValidator;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.user.role.domain.repository.RoleRepository;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import com.khaled_amin.book_social_network.identity.user.role.exception.RoleBusinessException;
import com.khaled_amin.book_social_network.identity.user.role.exception.RoleTechnicalException;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleFactory roleFactory;
    private final ActorProvider actorProvider;
    private final CapabilityService capabilityService;
    private final RoleValidator roleValidator;
    private final BusinessEventLogger businessEventLogger;

    public static final String DEFAULT_ROLES_CACHE = "defaultRoles";


    @Transactional
    @CacheEvict(value = DEFAULT_ROLES_CACHE, allEntries = true)
    @Override
    public Role createBusinessRole(RoleCreateRequest request) {

        // Application validation
        roleValidator.ensureCanBeCreate(request);

        // Domain logic
        Role role = roleFactory.createBusinessRole(
                request.getName(),
                request.getDisplayName(),
                request.getDescription(),
                request.getDefaultRole(),
                request.getProtectedRole()
        );

        // Persistence
        Role savedRole = roleRepository.save(role);

        businessEventLogger.businessRoleCreated(
                savedRole.getName()
        );

        return savedRole;
    }

    @Transactional
    @CacheEvict(value = DEFAULT_ROLES_CACHE, allEntries = true)
    @Override
    public Role createSystemRole(SystemRole systemRole){
        if (systemRole == null){
           throw RoleTechnicalException.nullSystemRole();
        }

        // Application validation
        roleValidator.ensureCanBeCreate(systemRole);

        // Domain logic
        Role role = roleFactory.createSystemRole(systemRole);

        // Persistence
        Role savedRole = roleRepository.save(role);

        businessEventLogger.systemRoleCreated(
                savedRole.getName()
        );

        return savedRole;

    }

    @Transactional
    @CacheEvict(value = DEFAULT_ROLES_CACHE, allEntries = true)
    @Override
    public Role update(RoleName roleName, RoleUpdateRequest request) {

        Role existingRole = getByName(roleName);

        // Application validation
        roleValidator.ensureCanBeUpdate(existingRole, request);

        RoleUpdateCommand command = RoleUpdateCommand.of(
                request.getDisplayName(),
                request.getDescription(),
                request.getDefaultRole(),
                request.getProtectedRole()
        );

        // Domain logic
        existingRole.update(command);

        // Persistence
        Role savedRole = roleRepository.save(existingRole);

        businessEventLogger.roleUpdated(
                savedRole.getName()
        );

        return savedRole;
    }

    @Transactional
    @CacheEvict(value = DEFAULT_ROLES_CACHE, allEntries = true)
    @Override
    public void delete(RoleName roleName) {

        Role role = getByName(roleName);

        // Application validation
        roleValidator.ensureCanBeDelete(role);

        // Domain logic
        role.delete();

        // Persistence
        roleRepository.delete(role);

        businessEventLogger.roleDeleted(
                role.getName()
        );
    }

    @Transactional
    @Override
    public Role addCapability(RoleName roleName, CapabilityCode code) {

        Role role = getByName(roleName);

        Capability capability = capabilityService.getByCode(code);

        Actor actor = actorProvider.getCurrent();

        // Application validation
        roleValidator.ensureCaAddCapability(capability,actor);

        // Domain logic
        role.addCapability(capability);

        // Persistence
        Role savedRole = roleRepository.save(role);

        businessEventLogger.roleCapabilityAssigned(
                role.getName(),
                capability.getCode()
        );

        return savedRole;
    }


    @Transactional
    @Override
    public Role removeCapability(RoleName roleName, CapabilityCode code) {

        Role role = getByName(roleName);
        Capability capability = capabilityService.getByCode(code);
        Actor actor = actorProvider.getCurrent();

        // Application validation
        roleValidator.ensureCaRemoveCapability(capability,actor);

        // Domain logic
        role.removeCapability(capability);

        // Persistence
        Role savedRole = roleRepository.save(role);

        businessEventLogger.roleCapabilityRemoved(
                role.getName(),
                capability.getCode()
        );

        return savedRole;
    }



    @Transactional(readOnly = true)
    public Role viewRole(RoleName roleName) {

        Role role = getByName(roleName);

        businessEventLogger.roleViewed(
                role.getName()
        );

        return role;
    }


    @Transactional(readOnly = true)
    public List<Role> listRoles() {

        List<Role> roles = getAll();

        businessEventLogger.roleListed();

        return roles;
    }




    // ----------------------------------------- Retrieval methods ----------------------------------------- //

    @Cacheable(DEFAULT_ROLES_CACHE)
    @Override
    public List<Role> getDefaultRoles() {

        List<Role> roles = roleRepository.findDefaultRoles();

        if (roles.isEmpty()) {
            throw RoleTechnicalException.invalidSystemRoleConfiguration();
        }

        return roles;
    }

    @Override
    public List<String> getDefaultRoleNames() {
        return getDefaultRoles()
                .stream()
                .map(Role::getName)
                .toList();
    }


    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> getOptionalByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public Role getByName(String roleName) {
        return getOptionalByName(roleName).orElseThrow(() -> RoleBusinessException.notFound()
                .withClientDetails("reason", "Role not found for given name")
        );
    }

    @Override
    public Role getByName(RoleName roleName) {
        return getByName(roleName.value());
    }

    @Override
    public List<Role> getAllByNames(List<RoleName> roleNames) {

        if (roleNames == null || roleNames.isEmpty()) {
            return List.of();
        }

        List<String> names = roleNames.
                stream()
                .filter(Objects::nonNull)
                .map(RoleName::value)
                .distinct()
                .toList();

        List<Role> roles = roleRepository.findAllByNameIn(names);

        if (roles.size() != roleNames.size()) {

            Set<String> foundNames = roles.stream()
                    .map(Role::getName)
                    .collect(Collectors.toSet());

            List<String> notFoundNames = names.stream()
                    .filter(name -> !foundNames.contains(name))
                    .toList();

            throw RoleBusinessException.someRolesNotFound()
                    .withClientDetails("requestedRoleNames", names)
                    .withClientDetails("notFoundRoleNames", notFoundNames);
        }

        return roles;
    }

    // ------------------------------------- End Retrieval methods ----------------------------------------- //


}



