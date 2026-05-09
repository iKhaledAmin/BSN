package com.khaled_amin.book_social_network.identity.verification.application.service;


import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.verification.application.config.VerificationProperties;
import com.khaled_amin.book_social_network.identity.verification.application.dto.VerificationResult;
import com.khaled_amin.book_social_network.identity.verification.application.exception.VerificationApplicationException;
import com.khaled_amin.book_social_network.identity.verification.domain.model.TokenType;
import com.khaled_amin.book_social_network.identity.verification.domain.model.VerificationToken;
import com.khaled_amin.book_social_network.identity.verification.domain.repository.VerificationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Default implementation of {@link VerificationService}.
 *
 * <p>
 * Orchestrates the verification token lifecycle including:
 * generation, persistence, validation, and state transitions.
 * </p>
 *
 * <h3>Workflow Overview</h3>
 * <ul>
 *   <li>Resolve token configuration based on {@link TokenType}</li>
 *   <li>Create domain verification token</li>
 *   <li>Persist token for future validation</li>
 *   <li>Validate token upon request</li>
 *   <li>Update token state after successful validation</li>
 * </ul>
 *
 * <h3>Design Notes</h3>
 * <ul>
 *   <li>Follows orchestration pattern (no business logic leakage)</li>
 *   <li>Delegates lifecycle rules to {@link VerificationToken} aggregate</li>
 *   <li>Delegates persistence to {@link VerificationTokenRepository}</li>
 *   <li>Delegates configuration resolution to {@link VerificationProperties}</li>
 * </ul>
 *
 * <h3>Token Lifecycle</h3>
 * <ul>
 *   <li>Generated → persisted → validated (single-use)</li>
 *   <li>Expired or reused tokens are rejected by domain rules</li>
 * </ul>
 *
 * <h3>Execution Semantics</h3>
 * <ul>
 *   <li>Token generation is deterministic based on configuration</li>
 *   <li>Validation is stateful and enforces strict domain invariants</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Application-level failures are translated to {@link VerificationApplicationException}</li>
 *   <li>Domain-level violations are propagated without suppression</li>
 *   <li>No silent failures are allowed</li>
 * </ul>
 *
 * @see VerificationToken
 * @see VerificationTokenRepository
 * @see VerificationProperties
 */


@Service
@RequiredArgsConstructor
public class VerificationServiceImpl implements VerificationService {

    private final VerificationTokenRepository repository;
    private final VerificationProperties properties;

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

        return token.getCode();
    }

    @Override
    public VerificationResult validateToken(String code,TokenType type) {

        VerificationToken token = getByCode(code);

        token.canBeUsedFor(type);
        token.validate();

        repository.save(token);

        return new VerificationResult(
                token.getTarget(),
                token.getTokenType()
        );
    }

    private VerificationToken getByCode(String code){
        return repository.findOptionalByCode(code)
                .orElseThrow(VerificationApplicationException::tokenNotFound);
    }

}
