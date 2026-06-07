package com.khaled_amin.book_social_network.identity.user.role.application.service;

import com.khaled_amin.book_social_network.identity.capability.domain.value.CapabilityCode;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleCreateRequest;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleUpdateRequest;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface RoleService {


    Role createBusinessRole(RoleCreateRequest request);
    Role createSystemRole(SystemRole systemRole);
    Role update(RoleName roleName, RoleUpdateRequest request);
    void delete(RoleName roleName);


    Role addCapability(RoleName roleName, CapabilityCode code);
    Role removeCapability(RoleName roleName, CapabilityCode code);

    Role viewRole(RoleName roleName);
    List<Role> listRoles();


    List<Role> getDefaultRoles();
    List<String> getDefaultRoleNames();

    List<Role> getAll();

    Optional<Role> getOptionalByName(String roleName);
    Role getByName(String roleName);
    Role getByName(RoleName roleName);

    List<Role> getAllByNames(List<RoleName> roleNames);

}
