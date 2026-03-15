package com.khaled_amin.book_social_network.common.mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;



public interface BaseMapper<CREATE_REQUEST, UPDATE_REQUEST, RESPONSE, ENTITY> {

    // Create
    ENTITY toEntity(CREATE_REQUEST request);

    // Update
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    void updateEntity(UPDATE_REQUEST request, @MappingTarget ENTITY entity);

    // Response
    RESPONSE toResponse(ENTITY entity);

    //  deep clone
    ENTITY deepClone(ENTITY source);
}