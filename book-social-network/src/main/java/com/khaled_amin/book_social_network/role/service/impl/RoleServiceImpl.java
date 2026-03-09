package com.khaled_amin.book_social_network.role.service.impl;

import com.khaled_amin.book_social_network.role.model.dto.RoleRequest;
import com.khaled_amin.book_social_network.role.model.dto.RoleResponse;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import com.khaled_amin.book_social_network.role.model.mapper.RoleMapper;
import com.khaled_amin.book_social_network.role.repository.RoleRepo;
import com.khaled_amin.book_social_network.role.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@AllArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepo roleRepo;
    private final RoleMapper roleMapper;


    @Override
    public Role toEntity(RoleRequest request) {
        return roleMapper.toEntity(request);
    }

    @Override
    public RoleResponse toResponse(Role entity) {
        return roleMapper.toResponse(entity);
    }

    @Override
    public Boolean existsByName(String name) {
        return roleRepo.existsByName(name);
    }


    @Override
    public Role add(RoleRequest roleRequest) {

        throwExceptionIfRoleNameAlreadyExists(roleRequest.getName());

        roleRequest.setName(roleRequest.getName().toUpperCase());
        Role newRole = toEntity(roleRequest);

        newRole = roleRepo.save(newRole);

        return newRole;
    }


    @Override
    public Role update(Long roleId, RoleRequest roleRequest) {

        Role existingRole = getById(roleId);

        // handle Role name
        if (!existingRole.getName().equals(roleRequest.getName())) {
            throwExceptionIfRoleNameAlreadyExists(roleRequest.getName());
        }

        roleRequest.setName(roleRequest.getName().toUpperCase());
        roleMapper.updateEntity(roleRequest,existingRole);

        return roleRepo.save(existingRole);
    }

    @Override
    public List<Role> getAll(){
        return roleRepo.findAll();
    }

    @Override
    public Optional<Role> getOptionalById(Long roleId){
        return roleRepo.findById(roleId);
    }

    @Override
    public Role getById(Long roleId){
        return getOptionalById(roleId).orElseThrow(
                () -> new NoSuchElementException("Role not found!")
        );
    }


    @Override
    public Optional<Role> getOptionalByName(String roleName){
        return roleRepo.findByName(roleName);
    }

    @Override
    public Role getByName(String roleName){
        return getOptionalByName(roleName).orElseThrow(
                () -> new NoSuchElementException("Role not found!")
        );
    }

    private void throwExceptionIfRoleNameAlreadyExists(String categoryName) {
        if (existsByName(categoryName)) {
            // todo - Exception handling
            //throw new ConflictException("Role name already exists!");
        }

    }

}
