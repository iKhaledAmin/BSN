package com.Khaled_Amin.book_social_network.user.repository;

import com.Khaled_Amin.book_social_network.user.model.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRoleRepo extends JpaRepository<AccountRole , Long> {
}
