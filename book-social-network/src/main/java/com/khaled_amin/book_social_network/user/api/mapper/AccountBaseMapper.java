package com.khaled_amin.book_social_network.user.api.mapper;

import com.khaled_amin.book_social_network.core.mapper.BaseMapper;
import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.user.api.dto.AccountBaseResponse;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import org.mapstruct.Mapper;

@Mapper(
        config = GlobalMapperConfig.class,
        uses = {ProfileMapper.class}
)
public interface AccountBaseMapper extends BaseMapper<AccountBaseResponse, Account> {

}
