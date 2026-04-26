package com.khaled_amin.book_social_network.auth;

import com.khaled_amin.book_social_network.core.persistence.BaseRepository;

import java.util.Optional;

public interface TokenRepo extends BaseRepository<Token,Long> {
    Optional<Token> findByToken(String token);
}
