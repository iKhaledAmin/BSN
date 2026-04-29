package com.khaled_amin.book_social_network.user.api.mapper;

import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.user.api.dto.AccountRoleResponse;
import com.khaled_amin.book_social_network.user.domain.model.AccountRole;
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
