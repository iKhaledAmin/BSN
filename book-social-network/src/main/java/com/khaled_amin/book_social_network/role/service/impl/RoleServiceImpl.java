package com.khaled_amin.book_social_network.role.service.impl;

import com.khaled_amin.book_social_network.common.servise.EntityRetrievalService;
import com.khaled_amin.book_social_network.role.exception.RoleException;
import com.khaled_amin.book_social_network.role.model.dto.RoleRequest;
import com.khaled_amin.book_social_network.role.model.dto.RoleResponse;
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


    @Override
    public Role toEntity(RoleRequest request) {
        return roleMapper.toEntity(request);
    }

    @Override
    public RoleResponse toResponse(Role entity) {
        return roleMapper.toResponse(entity);
    }

    @Override
    public boolean existsByName(String name) {
        return roleRepo.existsByName(name);
    }


    @Transactional
    @Override
    public Role add(RoleRequest roleRequest) {

        String normalizedName = normalizeRoleName(roleRequest.getName());

        validateRoleNameUniqueness(normalizedName);

        roleRequest.setName(normalizedName);

        Role role = toEntity(roleRequest);

        return roleRepo.save(role);
    }


    @Transactional
    @Override
    public Role update(Long roleId, RoleRequest roleRequest) {

        Role existingRole = getById(roleId);

        String normalizedName = normalizeRoleName(roleRequest.getName());

        if (!existingRole.getName().equals(normalizedName)) {
            validateRoleNameUniqueness(normalizedName);
        }

        roleRequest.setName(normalizedName);

        roleMapper.updateEntity(roleRequest, existingRole);

        return roleRepo.save(existingRole);
    }


    public Optional<Role> getOptionalDefaultRole(){
        return roleRepo.findByDefaultRoleTrue();
    }

    @Cacheable("defaultRole")
    @Override
    public Role getDefaultRole() {

        return getOptionalDefaultRole()
                .orElseThrow(() -> RoleException.defaultRoleNotConfigured());
    }


    @CacheEvict(value = "defaultRole")
    @Transactional
    @Override
    public void assignDefaultRole(Long roleId) {
        roleRepo.assignDefaultRole(roleId);
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


    private void validateRoleNameUniqueness(String roleName) {
        if (roleRepo.existsByName(roleName))
            throw RoleException.alreadyExists();
    }

    private String normalizeRoleName(String name) {
        return name.toUpperCase();
    }
}


