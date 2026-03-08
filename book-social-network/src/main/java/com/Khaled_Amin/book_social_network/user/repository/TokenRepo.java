package com.Khaled_Amin.book_social_network.user.repository;

import com.Khaled_Amin.book_social_network.user.model.entity.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TokenRepo extends JpaRepository<Token,Long> {
    Optional<Token> findByToken(String token);
}
