package com.khaled_amin.book_social_network.user.repository;

import com.khaled_amin.book_social_network.common.repository.BaseRepository;
import com.khaled_amin.book_social_network.auth.model.entity.Token;

import java.util.Optional;

public interface TokenRepo extends BaseRepository<Token,Long> {
    Optional<Token> findByToken(String token);
}
