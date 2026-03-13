package com.khaled_amin.book_social_network.role.service;

import com.khaled_amin.book_social_network.role.model.dto.RoleRequest;
import com.khaled_amin.book_social_network.role.model.dto.RoleResponse;
import com.khaled_amin.book_social_network.role.model.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role toEntity(RoleRequest request);
    RoleResponse toResponse(Role entity);

    boolean existsByName(String name);


    Role add(RoleRequest roleRequest);
    Role update(Long roleId, RoleRequest roleRequest);

    Optional<Role> getOptionalDefaultRole();
    Role getDefaultRole();
    void assignDefaultRole(Long roleId);

    public List<Role> getAll();

    Optional<Role> getOptionalById(Long roleId);
    Role getById(Long roleId);

    Optional<Role> getOptionalByName(String roleName);
    Role getByName(String roleName);

}
