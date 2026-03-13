package com.khaled_amin.book_social_network.user.model.mapper;

import com.khaled_amin.book_social_network.common.mapper.BaseMapper;
import com.khaled_amin.book_social_network.common.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.role.model.mapper.RoleMapper;
import com.khaled_amin.book_social_network.user.model.dto.AccountRequest;
import com.khaled_amin.book_social_network.user.model.dto.AccountResponse;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
        config = GlobalMapperConfig.class,
        uses = {ProfileMapper.class, RoleMapper.class}
)
public interface AccountMapper extends BaseMapper<AccountRequest, AccountResponse, Account> {


    @Mapping(target = "password", ignore = true)
    @Mapping(target = "accountRoles", ignore = true)
    @Mapping(target = "profile", source = "profileRequest")
    @Override
    void updateEntity(AccountRequest request, @MappingTarget Account entity);
}