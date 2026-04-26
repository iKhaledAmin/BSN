package com.khaled_amin.book_social_network.email.application.port.out;

import com.khaled_amin.book_social_network.email.domain.model.Email;
import com.khaled_amin.book_social_network.email.infrastructure.config.EmailProperties;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Outbound port for email persistence and retrieval.
 *
 * <p>
 * Defines the boundary between the application layer and the underlying
 * persistence mechanism for {@link Email} aggregates.
 * </p>
 *
 * <p>
 * This interface represents a <b>port</b> in Clean Architecture and abstracts
 * all persistence concerns, allowing the application layer to remain independent
 * of the underlying data access technology.
 * </p>
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Persist {@link Email} aggregate state transitions</li>
 *   <li>Retrieve emails eligible for retry processing</li>
 * </ul>
 *
 * <h3>Usage</h3>
 * <ul>
 *   <li>Used by application services as part of email processing workflows</li>
 *   <li>Should be injected via dependency inversion</li>
 *   <li>Consumers must not depend on implementation details</li>
 * </ul>
 *
 * <h3>Persistence Semantics</h3>
 * <ul>
 *   <li>Save operations must persist the full aggregate state atomically</li>
 *   <li>Returned entities reflect the persisted state after storage</li>
 *   <li>No partial updates or inconsistent states are allowed</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Failures originating from the persistence layer are propagated as runtime exceptions</li>
 *   <li>Typical failures include connectivity issues, constraint violations, or transaction failures</li>
 *   <li>No silent failures are allowed</li>
 *   <li>Exception translation is the responsibility of the calling application layer</li>
 * </ul>
 *
 *
 * @see EmailProperties
 */
public interface EmailRepository {

    /**
     * Persists the given email aggregate.
     *
     * <h3>Contract</h3>
     * <ul>
     *   <li>Stores or updates the provided {@link Email} aggregate</li>
     *   <li>The returned instance reflects the persisted state</li>
     * </ul>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>{@code email} must not be {@code null}</li>
     * </ul>
     *
     * <h3>Side Effects</h3>
     * <ul>
     *   <li>Writes data to the persistence store</li>
     * </ul>
     *
     * <h3>Failure Handling</h3>
     * <ul>
     *   <li>Propagates runtime exceptions originating from the persistence layer</li>
     * </ul>
     *
     * @param email the email aggregate to persist
     * @return the persisted email instance
     */
    Email save(Email email);

    /**
     * Retrieves emails eligible for retry based on retry policy constraints.
     *
     * <h3>Contract</h3>
     * <ul>
     *   <li>Returns emails eligible for retry processing</li>
     *   <li>Eligibility is determined by retry count and last attempt timestamp</li>
     * </ul>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>{@code retryThreshold} must not be {@code null}</li>
     *   <li>{@code maxAttempts} must be not {@code null}</li>
     * </ul>
     *
     * <h3>Side Effects</h3>
     * <ul>
     *   <li>None (read-only operation)</li>
     * </ul>
     *
     * <h3>Failure Handling</h3>
     * <ul>
     *   <li>Propagates runtime exceptions originating from the persistence layer</li>
     * </ul>
     *
     * @param retryThreshold the cutoff timestamp for last retry attempt
     * @param maxAttempts the maximum number of retry attempts allowed
     * @return list of retryable email aggregates (possibly empty, never {@code null})
     *
     */
    List<Email> findRetryableEmails(LocalDateTime retryThreshold, int maxAttempts);
}