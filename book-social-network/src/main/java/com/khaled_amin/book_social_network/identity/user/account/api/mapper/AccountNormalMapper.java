    package com.khaled_amin.book_social_network.identity.user.account.api.mapper;

    import com.khaled_amin.book_social_network.core.mapper.BaseMapper;
    import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
    import com.khaled_amin.book_social_network.identity.user.role.api.mapper.RoleMapper;
    import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountNormalResponse;
    import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountUpdateRequest;
    import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountUpdateCommand;
    import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
    import org.mapstruct.Mapper;
    import org.mapstruct.Mapping;
    import org.springframework.beans.factory.annotation.Autowired;


    @Mapper(
            config = GlobalMapperConfig.class,
            uses = {AccountBaseMapper.class,RoleMapper.class,ProfileMapper.class}
    )
    public abstract class AccountNormalMapper implements BaseMapper<AccountNormalResponse, Account> {


        protected ProfileMapper profileMapper;

        @Autowired
        public void setProfileMapper(ProfileMapper profileMapper) {
            this.profileMapper = profileMapper;
        }


        @Mapping(target = "roles", source = "roles", qualifiedByName = "rolesToNames")
        @Override
        public abstract AccountNormalResponse toResponse(Account account);



        public AccountUpdateCommand toCommand(AccountUpdateRequest request) {
            if (request == null) return null;

            return AccountUpdateCommand.of(
                    request.getEmailAddress(),
                    profileMapper.toCommand(request.getProfileUpdateRequest())
            );
        }



    }