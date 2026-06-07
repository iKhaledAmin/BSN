package com.khaled_amin.book_social_network.identity.verification.application.service;


import com.khaled_amin.book_social_network.core.logging.audit.BusinessEventLogger;
import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.verification.application.config.VerificationProperties;
import com.khaled_amin.book_social_network.identity.verification.application.dto.VerificationResult;
import com.khaled_amin.book_social_network.identity.verification.exception.VerificationException;
import com.khaled_amin.book_social_network.identity.verification.domain.model.TokenType;
import com.khaled_amin.book_social_network.identity.verification.domain.model.VerificationToken;
import com.khaled_amin.book_social_network.identity.verification.domain.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationTokenRepository repository;
    private final VerificationProperties properties;
    private final BusinessEventLogger businessEventLogger;

    @Override
    public String generateToken(TokenType type, ActorIdentity target) {

        int codeLength = properties.getCodeLength(type);
        int expiration = properties.getExpirationMinutes(type);

        VerificationToken token = VerificationToken.create(
                type,
                target,
                codeLength,
                expiration
        );

        repository.save(token);

        businessEventLogger.verificationTokenGenerated(
                token.getId(),
                token.getTokenType().name(),
                token.getTarget().getActorType().toString(),
                token.getTarget().getActorCode().toString()
        );

        return token.getCode();
    }

    @Override
    public VerificationResult verifyToken(String code, TokenType type) {

        VerificationToken token = getOptionalByCode(code).orElseThrow(() -> VerificationException.invalidToken()
                .withDebugDetails("reason", "Token not found")
                .withDebugDetails("tokenCode",code)
        );

        token.canBeUsedFor(type);
        token.verify();

        repository.save(token);

        businessEventLogger.verificationTokenVerified(
                token.getId(),
                token.getTokenType().name(),
                token.getTarget().getActorType().toString(),
                token.getTarget().getActorCode().toString()
        );

        return new VerificationResult(
                token.getTarget(),
                token.getTokenType()
        );
    }


    private Optional<VerificationToken> getOptionalByCode(String code){
        return repository.findOptionalByCode(code);
    }

}
