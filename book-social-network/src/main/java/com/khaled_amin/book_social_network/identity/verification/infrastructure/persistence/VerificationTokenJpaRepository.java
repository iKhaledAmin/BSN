package com.khaled_amin.book_social_network.identity.verification.infrastructure.persistence;

import com.khaled_amin.book_social_network.core.persistence.BaseRepository;
import com.khaled_amin.book_social_network.identity.verification.domain.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenJpaRepository extends BaseRepository<VerificationToken,Long> {
    Optional<VerificationToken> findByCode(String code);
}
