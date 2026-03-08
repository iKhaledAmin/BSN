package com.Khaled_Amin.book_social_network.user.repository;

import com.Khaled_Amin.book_social_network.user.model.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileRepo extends JpaRepository<Profile, Long> {
}
