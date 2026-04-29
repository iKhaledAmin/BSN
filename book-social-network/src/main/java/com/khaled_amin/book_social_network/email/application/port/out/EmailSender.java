package com.khaled_amin.book_social_network.email.application.port.out;

import com.khaled_amin.book_social_network.email.application.exception.EmailApplicationException;
import com.khaled_amin.book_social_network.email.application.model.EmailMessage;




/**
 * Outbound port for email delivery.
 *
 * <p>
 * Defines the boundary between the application layer and external email
 * infrastructure (e.g., SMTP servers, third-party providers).
 * </p>
 *
 * <p>
 * This interface represents a <b>driven port</b> in Clean Architecture.
 * It abstracts the email delivery mechanism and allows the application
 * layer to remain independent of infrastructure concerns.
 * </p>
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Provide a mechanism for delivering email messages</li>
 *   <li>Abstract underlying transport and provider-specific details</li>
 * </ul>
 *
 * <h3>Usage</h3>
 * <ul>
 *   <li>Injected into application services</li>
 *   <li>Used as part of email-related use cases</li>
 *   <li>Consumers must depend only on this contract</li>
 * </ul>
 *
 * <h3>Execution Semantics</h3>
 * <ul>
 *   <li>Delivery is delegated to the underlying infrastructure implementation</li>
 *   <li>No guarantee of delivery latency or eventual consistency</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Failures are propagated as {@link EmailApplicationException}</li>
 *   <li>No silent failures are allowed</li>
 * </ul>
 *
 * <h3>Implementation Constraints</h3>
 * <ul>
 *   <li>Implementations should be stateless and thread-safe</li>
 *   <li>Must not store request-specific state in instance fields</li>
 *   <li>Must translate infrastructure exceptions to {@link EmailApplicationException}</li>
 * </ul>
 *
 * @see EmailMessage
 */
public interface EmailSender {

    /**
     * Sends an email message using the configured delivery mechanism.
     *
     * <h3>Contract</h3>
     * <ul>
     *   <li>Attempts to deliver the provided email message</li>
     *   <li>Delegates delivery to the underlying infrastructure</li>
     * </ul>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>{@code message} must not be {@code null}</li>
     *   <li>Must contain at least one valid recipient address</li>
     * </ul>
     *
     * <h3>Side Effects</h3>
     * <ul>
     *   <li>Triggers external communication with an email delivery system</li>
     * </ul>
     *
     * <h3>Failure Handling</h3>
     * <ul>
     *   <li>Throws {@link EmailApplicationException} if delivery fails</li>
     * </ul>
     *
     * @param message {@link EmailMessage} the email message to be sent
     * @throws EmailApplicationException if the email could not be delivered
     */
    void send(EmailMessage message);
}