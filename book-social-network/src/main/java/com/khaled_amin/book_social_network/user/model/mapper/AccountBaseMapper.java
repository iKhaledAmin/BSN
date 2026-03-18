package com.khaled_amin.book_social_network.user.model.mapper;

import com.khaled_amin.book_social_network.common.mapper.BaseMapper;
import com.khaled_amin.book_social_network.common.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.user.model.dto.AccountBaseResponse;
import com.khaled_amin.book_social_network.user.model.dto.AccountUpdateRequest;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import org.mapstruct.Mapper;

@Mapper(
        config = GlobalMapperConfig.class,
        uses = {ProfileMapper.class}
)
public interface AccountBaseMapper extends BaseMapper<AccountUpdateRequest, AccountUpdateRequest, AccountBaseResponse, Account> {

}
