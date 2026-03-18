package com.khaled_amin.book_social_network.user.model.mapper;

import com.khaled_amin.book_social_network.common.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.user.model.dto.AccountRoleResponse;
import com.khaled_amin.book_social_network.user.model.entity.AccountRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = GlobalMapperConfig.class
)
public interface AccountRoleMapper {

    @Mapping(target = "roleId", source = "role.id")
    @Mapping(target = "roleName", source = "role.name")
    AccountRoleResponse toResponse(AccountRole entity);
}
