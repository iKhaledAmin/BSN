package com.khaled_amin.book_social_network.identity.verification.application.service;

import com.khaled_amin.book_social_network.identity.actor.ActorIdentity;
import com.khaled_amin.book_social_network.identity.verification.application.dto.VerificationResult;
import com.khaled_amin.book_social_network.identity.verification.application.exception.VerificationApplicationException;
import com.khaled_amin.book_social_network.identity.verification.domain.model.TokenType;

/**
 * Inbound port for verification-related application use cases.
 *
 * <p>
 * Defines the application-level contract for generating and validating
 * verification tokens associated with a specific {@link ActorIdentity}.
 * </p>
 *
 * <p>
 * This interface represents a <b>use-case boundary</b> in Clean Architecture.
 * It is the primary entry point for external actors (e.g., authentication flows,
 * account management services, or API controllers) to interact with the
 * verification subsystem.
 * </p>
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Generate verification tokens for a specific purpose (e.g., account activation, password reset)</li>
 *   <li>Validate submitted tokens and enforce lifecycle constraints</li>
 *   <li>Return verification results required for downstream workflows</li>
 * </ul>
 *
 * <h3>Usage</h3>
 * <ul>
 *   <li>Consumed by higher-level application services (e.g., AccountService, AuthService)</li>
 *   <li>Consumers must not depend on implementation details</li>
 * </ul>
 *
 * <h3>Execution Semantics</h3>
 * <ul>
 *   <li>Token generation creates a new verification record with a defined lifecycle</li>
 *   <li>Token validation enforces domain invariants (expiration, single-use)</li>
 *   <li>Successful validation returns the associated target and token type</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Failures are propagated as {@link VerificationApplicationException}</li>
 *   <li>Domain-level violations (e.g., expired or already used tokens) are surfaced transparently</li>
 *   <li>No silent failures are allowed</li>
 * </ul>
 *
 * @see ActorIdentity
 * @see VerificationApplicationException
 */
public interface VerificationService {

    /**
     * Generates a new verification token for the specified type and target.
     *
     * <h3>Contract</h3>
     * <ul>
     *   <li>Creates a token with configuration derived from {@link TokenType}</li>
     *   <li>Associates the token with the provided {@link ActorIdentity}</li>
     *   <li>Persists the token for future validation</li>
     *   <li>Returns the generated token code</li>
     * </ul>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>{@code type} must not be {@code null}</li>
     *   <li>{@code target} must not be {@code null}</li>
     * </ul>
     *
     * <h3>Side Effects</h3>
     * <ul>
     *   <li>Persists a new verification token</li>
     * </ul>
     *
     * <h3>Failure Handling</h3>
     * <ul>
     *   <li>Throws {@link VerificationApplicationException} if token generation fails</li>
     * </ul>
     *
     * @param type  {@link TokenType}  the verification purpose (e.g., account activation, password reset)
     * @param target {@link ActorIdentity} the actor identity associated with the token
     * @return generated verification code
     */
    String generateToken(TokenType type, ActorIdentity target);


    /**
     * Validates a verification token based on its code.
     *
     * <h3>Contract</h3>
     * <ul>
     *   <li>Retrieves the token associated with the provided code</li>
     *   <li>Validates token lifecycle constraints (not expired, not previously used)</li>
     *   <li>Marks the token as used upon successful validation</li>
     *   <li>Returns verification result for downstream processing</li>
     * </ul>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>{@code code} must not be {@code null} or blank</li>
     *   <li>{@code type} must not be {@code null}</li>
     * </ul>
     *
     * <h3>Side Effects</h3>
     * <ul>
     *   <li>Updates token state (marks as validated)</li>
     * </ul>
     *
     * <h3>Failure Handling</h3>
     * <ul>
     *   <li>Throws {@link VerificationApplicationException} if token is not found</li>
     *   <li>Throws domain exceptions if token is expired or already used</li>
     * </ul>
     *
     * @param code {@link String} the verification token code
     * @param type {@link TokenType} the type of token (e.g., activation, password reset)  used in validation process
     * @return verification {@link VerificationResult} result containing target and token type
     */
    VerificationResult validateToken(String code, TokenType type);
}