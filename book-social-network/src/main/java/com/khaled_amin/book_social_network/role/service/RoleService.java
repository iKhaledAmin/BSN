package com.khaled_amin.book_social_network.role.service;

import com.khaled_amin.book_social_network.role.exception.RoleException;
import com.khaled_amin.book_social_network.role.model.dto.CreateRoleRequest;
import com.khaled_amin.book_social_network.role.model.dto.UpdateRoleRequest;
import com.khaled_amin.book_social_network.role.model.entity.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {


    Role add(CreateRoleRequest request);
    Role update(Long roleId, UpdateRoleRequest request);

    List<Role> getDefaultRoles();

    List<Role> getAll();

    Optional<Role> getOptionalById(Long roleId);
    Role getById(Long roleId);

    boolean existsByName(String name);

    Optional<Role> getOptionalByName(String roleName);
    Role getByName(String roleName);

    Optional<Role> getOptionalBySystemCode(String systemCode);
    Role getBySystemCode(String systemCode);

    void delete(Long roleId);

}
