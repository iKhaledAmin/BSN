package com.khaled_amin.book_social_network.identity.user.role.application.service;

import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.CreateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.UpdateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleId;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleService {


    Role createBusinessRole(CreateRoleCommand command);
    Role createSystemRole(SystemRole systemRole);
    Role update(RoleName roleName, UpdateRoleCommand command);

    Role addCapability(RoleName roleName, CapabilityCode code);
    Role removeCapability(RoleName roleName, CapabilityCode code);

    List<Role> getDefaultRoles();

    List<Role> getAll();

    Optional<Role> getOptionalById(Long roleId);
    Role getById(Long roleId);
    Role getById(RoleId roleId);

    Optional<Role> getOptionalByName(String roleName);
    Role getByName(String roleName);
    Role getByName(RoleName roleName);


    void delete(RoleName roleName);

    List<Role> getAllByNames(List<RoleName> roleNames);
}
