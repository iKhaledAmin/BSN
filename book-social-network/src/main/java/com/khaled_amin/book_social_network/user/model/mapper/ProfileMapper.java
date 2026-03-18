package com.khaled_amin.book_social_network.user.model.mapper;

import com.khaled_amin.book_social_network.common.mapper.BaseMapper;
import com.khaled_amin.book_social_network.common.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.user.model.dto.UpdateProfileRequest;
import com.khaled_amin.book_social_network.user.model.dto.ProfileResponse;
import com.khaled_amin.book_social_network.user.model.entity.Profile;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface ProfileMapper extends BaseMapper<UpdateProfileRequest, UpdateProfileRequest,ProfileResponse, Profile> {
}
