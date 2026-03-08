package com.Khaled_Amin.book_social_network.user.repository;

import com.Khaled_Amin.book_social_network.user.model.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepo extends JpaRepository<Account,Long> {
    Optional<Account> findByUsername(String username);
}
