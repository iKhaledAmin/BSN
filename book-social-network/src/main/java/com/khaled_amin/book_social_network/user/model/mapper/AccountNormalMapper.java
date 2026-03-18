package com.khaled_amin.book_social_network.user.model.mapper;

import com.khaled_amin.book_social_network.common.mapper.BaseMapper;
import com.khaled_amin.book_social_network.common.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.role.model.mapper.RoleMapper;
import com.khaled_amin.book_social_network.user.model.dto.AccountNormalResponse;
import com.khaled_amin.book_social_network.user.model.dto.AccountUpdateRequest;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import org.mapstruct.*;


@Mapper(
        config = GlobalMapperConfig.class,
        uses = {AccountBaseMapper.class,RoleMapper.class}
)
public interface AccountNormalMapper extends BaseMapper<AccountUpdateRequest, AccountUpdateRequest, AccountNormalResponse, Account> {

    @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToNames")
    @Override
    AccountNormalResponse toResponse(Account account);



    @Mapping(target = "password", ignore = true)
    @Mapping(target = "accountRoles", ignore = true)
    @Mapping(target = "profile", source = "updateProfileRequest")
    @Override
    void updateEntity(AccountUpdateRequest request, @MappingTarget Account entity);



}