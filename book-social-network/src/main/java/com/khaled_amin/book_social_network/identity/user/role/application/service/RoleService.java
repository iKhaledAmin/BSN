package com.khaled_amin.book_social_network.identity.user.role.application.service;

import com.khaled_amin.book_social_network.identity.user.role.domain.command.CreateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.UpdateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleId;

import java.util.List;
import java.util.Optional;

public interface RoleService {


    Role createBusinessRole(CreateRoleCommand command);
    Role createSystemRole(SystemRole systemRole);
    Role update(Long roleId, UpdateRoleCommand command);

    List<Role> getDefaultRoles();

    List<Role> getAll();

    Optional<Role> getOptionalById(Long roleId);
    Role getById(Long roleId);
    Role getById(RoleId roleId);

    Optional<Role> getOptionalByName(String roleName);
    Role getByName(String roleName);


    void delete(RoleId roleId);

    List<Role> getAllByIds(List<Long> roleIds);

    List<Long> getAllDefaultRoleIds();
}
