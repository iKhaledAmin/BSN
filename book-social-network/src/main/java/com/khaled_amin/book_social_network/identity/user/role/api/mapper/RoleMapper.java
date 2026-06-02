package com.khaled_amin.book_social_network.identity.user.role.api.mapper;


import com.khaled_amin.book_social_network.core.mapper.BaseMapper;
import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.identity.user.role.api.dto.RoleResponse;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.Set;


@Mapper(
        config = GlobalMapperConfig.class,
        uses = {RoleCapabilityMapper.class}
)
public interface RoleMapper extends BaseMapper<RoleResponse,Role> {

    @Mapping(target = "capabilities", source = "roleCapabilities")
    @Override
    RoleResponse toResponse(Role role);

    @Named("roleToName")
    default String map(Role role) {
        return role.getName();
    }

    @Named("rolesToNames")
    default List<String> mapList(Set<Role> roles) {
        if (roles == null) return List.of();
        return roles.stream()
                .map(Role::getName)
                .toList();
    }


}
