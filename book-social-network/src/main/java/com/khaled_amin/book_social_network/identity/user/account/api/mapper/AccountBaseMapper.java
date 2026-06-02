package com.khaled_amin.book_social_network.identity.user.account.api.mapper;

import com.khaled_amin.book_social_network.core.mapper.BaseMapper;
import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.identity.core.mapper.IdentityMapper;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountBaseResponse;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import org.mapstruct.Mapper;


@Mapper(
        config = GlobalMapperConfig.class,
        uses = {
                IdentityMapper.class,
                ProfileMapper.class
        }
)
public interface AccountBaseMapper extends BaseMapper<AccountBaseResponse, Account> {

}
