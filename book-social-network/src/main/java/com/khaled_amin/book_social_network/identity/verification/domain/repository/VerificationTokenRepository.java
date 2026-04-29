package com.khaled_amin.book_social_network.identity.verification.domain.repository;

import com.khaled_amin.book_social_network.identity.verification.domain.model.VerificationToken;

import java.util.Optional;

public interface VerificationTokenRepository {
    VerificationToken save(VerificationToken token);

    Optional<VerificationToken> findOptionalByCode(String code);
}
