package com.khaled_amin.book_social_network.role.model.mapper;

import com.khaled_amin.book_social_network.common.mapper.BaseMapper;
import com.khaled_amin.book_social_network.common.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.role.model.dto.CreateRoleRequest;
import com.khaled_amin.book_social_network.role.model.dto.RoleResponse;
import com.khaled_amin.book_social_network.role.model.dto.UpdateRoleRequest;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

import java.util.List;


@Mapper(config = GlobalMapperConfig.class)
public interface RoleMapper extends BaseMapper<CreateRoleRequest, UpdateRoleRequest,RoleResponse,Role> {

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

}
