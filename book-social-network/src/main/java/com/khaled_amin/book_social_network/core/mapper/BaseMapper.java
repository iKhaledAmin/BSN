package com.khaled_amin.book_social_network.core.mapper;


public interface BaseMapper<RESPONSE, ENTITY> {

    // Response
    RESPONSE toResponse(ENTITY entity);
}