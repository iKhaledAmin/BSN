package com.khaled_amin.book_social_network.user.api.mapper;

import com.khaled_amin.book_social_network.core.mapper.BaseMapper;
import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.user.api.dto.AccountAdminResponse;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(
        config = GlobalMapperConfig.class,
        uses = {AccountBaseMapper.class, AccountRoleMapper.class}
)
public interface AccountAdminMapper extends BaseMapper<AccountAdminResponse, Account> {

    @Mapping(target = "detailedRoles", source = "accountRoles")
    @Override
    AccountAdminResponse toResponse(Account account);



}