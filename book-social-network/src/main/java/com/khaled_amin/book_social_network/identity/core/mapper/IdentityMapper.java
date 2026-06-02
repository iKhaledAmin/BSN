package com.khaled_amin.book_social_network.identity.core.mapper;

import com.khaled_amin.book_social_network.core.mapper.GlobalMapperConfig;
import com.khaled_amin.book_social_network.identity.core.dto.IdentityResponse;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import org.mapstruct.Mapper;

@Mapper(config = GlobalMapperConfig.class)
public interface IdentityMapper {

    IdentityResponse toResponse(ActorIdentity identity);

    default String map(ActorCode actorCode) {
        return actorCode == null ? null : actorCode.toString();
    }

}