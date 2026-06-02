package com.khaled_amin.book_social_network.identity.user.account.api.mapper;

import com.khaled_amin.book_social_network.core.mapper.BaseMapper;
import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.identity.core.mapper.IdentityMapper;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountAdminResponse;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(
        config = GlobalMapperConfig.class,
        uses = {
                IdentityMapper.class,
                AccountBaseMapper.class,
                AccountRoleMapper.class
        }
)
public interface AccountAdminMapper extends BaseMapper<AccountAdminResponse, Account> {

    @Mapping(target = "roles", source = "accountRoles")
    @Override
    AccountAdminResponse toResponse(Account account);

}