package com.khaled_amin.book_social_network.identity.verification.infrastructure.persistence;

import com.khaled_amin.book_social_network.identity.verification.domain.model.VerificationToken;
import com.khaled_amin.book_social_network.identity.verification.domain.repository.VerificationTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@AllArgsConstructor
@Repository
public class VerificationVerificationTokenRepositoryImpl implements VerificationTokenRepository {

    private final VerificationTokenJpaRepository verificationTokenJpaRepository;

    @Override
    public VerificationToken save(VerificationToken verificationToken) {
        return verificationTokenJpaRepository.save(verificationToken);
    }

    @Override
    public Optional<VerificationToken> findOptionalByCode(String code) {
        return verificationTokenJpaRepository.findByCode(code);
    }

}
