package com.khaled_amin.book_social_network.identity.user.account.api.mapper;

import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.identity.core.mapper.IdentityMapper;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountRoleResponse;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.AccountRole;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        config = GlobalMapperConfig.class,
        uses = IdentityMapper.class
)
public interface AccountRoleMapper {

    @Mapping(target = "roleName", source = "role.name")
    @Mapping(target = "assignedAt", source = "createdAt")
    @Mapping(target = "assignedBy", source = "createdBy")
    AccountRoleResponse toResponse(AccountRole entity);
}
