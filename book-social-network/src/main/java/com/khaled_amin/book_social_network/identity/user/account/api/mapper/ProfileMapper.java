package com.khaled_amin.book_social_network.identity.user.account.api.mapper;

import com.khaled_amin.book_social_network.core.mapper.BaseMapper;
import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.ProfileUpdateRequest;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.ProfileResponse;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.ProfileUpdateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Profile;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface ProfileMapper extends BaseMapper<ProfileResponse, Profile> {

    default ProfileUpdateCommand toCommand(ProfileUpdateRequest request) {
        if (request == null) return null;

        return ProfileUpdateCommand.of(
                request.getFirstName(),
                request.getLastName(),
                request.getGender(),
                request.getBirthDate(),
                request.getPhoneNumber(),
                request.getProfession()
        );
    }
}
