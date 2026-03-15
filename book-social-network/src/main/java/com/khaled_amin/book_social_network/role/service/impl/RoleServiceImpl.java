package com.khaled_amin.book_social_network.role.service.impl;

import com.khaled_amin.book_social_network.common.servise.EntityRetrievalService;
import com.khaled_amin.book_social_network.role.exception.RoleException;
import com.khaled_amin.book_social_network.role.model.dto.CreateRoleRequest;
import com.khaled_amin.book_social_network.role.model.dto.UpdateRoleRequest;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import com.khaled_amin.book_social_network.role.model.mapper.RoleMapper;
import com.khaled_amin.book_social_network.role.repository.RoleRepo;
import com.khaled_amin.book_social_network.role.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;
    private final RoleMapper roleMapper;
    private final EntityRetrievalService entityRetrievalService;

    public static final String DEFAULT_ROLES_CACHE = "defaultRoles";



    @Override
    public boolean existsByName(String name) {
        return roleRepo.existsByName(name);
    }


    @Transactional
    @CacheEvict(value = DEFAULT_ROLES_CACHE, allEntries = true)
    @Override
    public Role add(CreateRoleRequest request) {

        String normalizedName = normalizeRoleName(request.getName());

        validateRoleNameUniqueness(normalizedName);

        request.setName(normalizedName);

        Role role = roleMapper.toEntity(request);

        return roleRepo.save(role);
    }


    @Transactional
    @CacheEvict(value = DEFAULT_ROLES_CACHE, allEntries = true)
    @Override
    public Role update(Long roleId, UpdateRoleRequest request) {

        Role existingRole = getById(roleId);

        String normalizedName = normalizeRoleName(request.getName());

        if (!existingRole.getName().equals(normalizedName)) {
            validateRoleNameUniqueness(normalizedName);
        }

        request.setName(normalizedName);

        roleMapper.updateEntity(request, existingRole);

        return roleRepo.save(existingRole);
    }


    @Cacheable(DEFAULT_ROLES_CACHE)
    @Override
    public List<Role> getDefaultRoles() {

        List<Role> roles = roleRepo.findAllByDefaultRoleTrue();

        if (roles.isEmpty()) {
            throw RoleException.defaultRoleNotConfigured();
        }

        return roles;
    }



    @Override
    public List<Role> getAll(){
        return roleRepo.findAll();
    }

    @Override
    public Optional<Role> getOptionalById(Long roleId){
        return entityRetrievalService.getOptionalById(Role.class,roleId);
    }

    @Override
    public Role getById(Long roleId) {
        return entityRetrievalService.getById(Role.class,roleId,RoleException::notFound);
    }

    @Override
    public Optional<Role> getOptionalByName(String roleName){
        return roleRepo.findByName(roleName);
    }

    @Override
    public Role getByName(String roleName) {
        return getOptionalByName(roleName)
                .orElseThrow(RoleException::notFound);
    }


    @Transactional
    @CacheEvict(value = DEFAULT_ROLES_CACHE, allEntries = true)
    @Override
    public void delete(Long roleId) {

        Role role = getById(roleId);

        if (role.isProtectedRole() || role.getSystemCode() != null) {
            throw RoleException.protectedRole();
        }

        roleRepo.delete(role);
    }

    private void validateRoleNameUniqueness(String roleName) {
        if (existsByName(roleName))
            throw RoleException.alreadyExists();
    }

    private String normalizeRoleName(String name) {
        return name.toUpperCase();
    }
}


