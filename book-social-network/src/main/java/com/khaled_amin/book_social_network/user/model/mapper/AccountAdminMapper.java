package com.khaled_amin.book_social_network.user.model.mapper;

import com.khaled_amin.book_social_network.common.mapper.BaseMapper;
import com.khaled_amin.book_social_network.common.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.user.model.dto.AccountAdminResponse;
import com.khaled_amin.book_social_network.user.model.dto.AccountUpdateRequest;
import com.khaled_amin.book_social_network.user.model.dto.UpdateProfileRequest;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(
        config = GlobalMapperConfig.class,
        uses = {AccountBaseMapper.class, AccountRoleMapper.class}
)
public interface AccountAdminMapper extends
        BaseMapper<AccountUpdateRequest, UpdateProfileRequest, AccountAdminResponse, Account> {

    @Mapping(target = "detailedRoles", source = "accountRoles")
    @Override
    AccountAdminResponse toResponse(Account account);



}
