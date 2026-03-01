package com.Khaled_Amin.book_social_network.user.repository;

import com.Khaled_Amin.book_social_network.user.model.entity.Token;

import java.util.Optional;

public interface TokenRepo {
    Optional<Token> findByToken(String token);
}
