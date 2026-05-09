package com.khaled_amin.book_social_network.identity.user.role.api.mapper;

import com.khaled_amin.book_social_network.core.mapper.BaseMapper;
import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.CreateRoleRequest;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleResponse;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.UpdateRoleRequest;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.CreateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.UpdateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDescription;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDisplayName;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;


@Mapper(config = GlobalMapperConfig.class)
public interface RoleMapper extends BaseMapper<RoleResponse,Role> {


    @Named("roleToName")
    default String map(Role role) {
        return role.getName();
    }

    @Named("rolesToNames")
    default List<String> mapList(List<Role> roles) {
        if (roles == null) return List.of();
        return roles.stream()
                .map(Role::getName)
                .toList();
    }


    @Mapping(target = "name", expression = "java(RoleName.of(request.getName()))")
    @Mapping(target = "displayName", expression = "java(RoleDisplayName.of(request.getDisplayName()))")
    @Mapping(target = "description", expression = "java(RoleDescription.of(request.getDescription()))")
    @Mapping(target = "defaultRole", source = "defaultRole")
    @Mapping(target = "protectedRole", source = "protectedRole")
    CreateRoleCommand toCommand(CreateRoleRequest request);


    UpdateRoleCommand toCommand(UpdateRoleRequest request);


    // ---------- Helpers ----------

    default RoleDisplayName mapDisplayName(String value) {
        return value == null ? null : RoleDisplayName.of(value);
    }

    default RoleDescription mapDescription(String value) {
        return value == null ? null : RoleDescription.of(value);
    }

}
