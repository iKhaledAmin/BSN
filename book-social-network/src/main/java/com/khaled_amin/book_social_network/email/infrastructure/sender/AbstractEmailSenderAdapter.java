package com.khaled_amin.book_social_network.email.infrastructure.sender;

import com.khaled_amin.book_social_network.email.application.exception.EmailApplicationException;
import com.khaled_amin.book_social_network.email.application.model.EmailMessage;
import com.khaled_amin.book_social_network.email.application.port.out.EmailSender;

/**
 * Abstract base implementation of {@link EmailSender} that enforces
 * consistent exception handling across all email sender adapters.
 *
 * <p>
 * Implements the <b>Template Method pattern</b> to standardize the execution
 * flow of email delivery while delegating the actual sending logic to subclasses.
 * </p>
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Provide a unified entry point for email delivery</li>
 *   <li>Handle exception translation from infrastructure to application layer</li>
 *   <li>Delegate actual sending logic to subclasses</li>
 * </ul>
 *
 * <h3>Execution Flow</h3>
 * <ul>
 *   <li>Invoke {@link #doSend(EmailMessage)}</li>
 *   <li>Catch and translate infrastructure exceptions</li>
 *   <li>Ensure only {@link EmailApplicationException} escapes the boundary</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Re-throws {@link EmailApplicationException} without modification</li>
 *   <li>Wraps any other exception into {@link EmailApplicationException}</li>
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
        } catch (EmailApplicationException ex) {
            throw ex; // rethrow application exception
        } catch (Exception ex) {
            throw EmailApplicationException.sendFailed(ex); // translate to application exception
        }
    }

    /**
     * Performs the actual email sending logic.
     *
     * <p>
     * This method is implemented by concrete adapters to integrate
     * with specific email delivery mechanisms (e.g., SMTP, APIs).
     * </p>
     *
     * <h3>Contract</h3>
     * <ul>
     *   <li>Execute the delivery of the given email message</li>
     * </ul>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>Must not perform exception translation</li>
     *   <li>Any thrown exception will be handled by the template method</li>
     * </ul>
     *
     * @param message the email message to send
     * @throws Exception any exception during delivery (will be translated)
     *
     * @see EmailSender#send(EmailMessage)
     */
    protected abstract void doSend(EmailMessage message) throws Exception;
}