package com.khaled_amin.book_social_network.email.infrastructure.sender;

import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;
import com.khaled_amin.book_social_network.email.application.model.EmailMessage;
import com.khaled_amin.book_social_network.email.application.port.out.EmailSender;
import com.khaled_amin.book_social_network.email.exception.EmailTechnicalException;

/**
 * Abstract base implementation of {@link EmailSender} that enforces
 * consistent exception handling across all emailAddress sender adapters.
 *
 * <p>
 * Implements the <b>Template Method pattern</b> to standardize the execution
 * flow of emailAddress delivery while delegating the actual sending logic to subclasses.
 * </p>
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Provide a unified entry point for emailAddress delivery</li>
 *   <li>Handle exception translation from infrastructure to application layer</li>
 *   <li>Delegate actual sending logic to subclasses</li>
 * </ul>
 *
 * <h3>Execution Flow</h3>
 * <ul>
 *   <li>Invoke {@link #doSend(EmailMessage)}</li>
 *   <li>Catch and translate infrastructure exceptions</li>
 *   <li>Ensure only {@link TechnicalException} escapes the boundary</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Re-throws {@link TechnicalException} without modification</li>
 *   <li>Wraps any other exception into {@link EmailTechnicalException}</li>
 * </ul>
 *
 * <h3>Extension Guidelines</h3>
 * <ul>
 *   <li>Subclasses must implement {@link #doSend(EmailMessage)}</li>
 *   <li>Subclasses must NOT perform exception translation</li>
 *   <li>All exceptions should be allowed to propagate</li>
 * </ul>
 *
 *
 * @see EmailSender
 * @see EmailMessage
 */
public abstract class AbstractEmailSenderAdapter implements EmailSender {

    @Override
    public final void send(EmailMessage message) {
        try {
            doSend(message);
        } catch (TechnicalException ex) {
            throw ex; // rethrow application exception
        } catch (Exception ex) {
            throw EmailTechnicalException.emailSendingFailed(ex); //
        }
    }

    /**
     * Performs the actual emailAddress sending logic.
     *
     * <p>
     * This method is implemented by concrete adapters to integrate
     * with specific emailAddress delivery mechanisms (e.g., SMTP, APIs).
     * </p>
     *
     * <h3>Contract</h3>
     * <ul>
     *   <li>Execute the delivery of the given emailAddress message</li>
     * </ul>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>Must not perform exception translation</li>
     *   <li>Any thrown exception will be handled by the template method</li>
     * </ul>
     *
     * @param message the emailAddress message to send
     * @throws TechnicalException any exception during delivery (will be translated)
     *
     * @see EmailSender#send(EmailMessage)
     */
    protected abstract void doSend(EmailMessage message) throws Exception;
}