package com.khaled_amin.book_social_network.email.application.port.in;

import com.khaled_amin.book_social_network.email.application.exception.EmailApplicationException;
import com.khaled_amin.book_social_network.email.application.model.EmailMessage;
import com.khaled_amin.book_social_network.email.application.port.out.EmailRepository;
import com.khaled_amin.book_social_network.email.application.port.out.EmailSender;
import com.khaled_amin.book_social_network.email.application.port.out.TemplateRenderer;
import com.khaled_amin.book_social_network.email.domain.command.EmailCreateCommand;

import java.time.LocalDateTime;
import java.util.Map;



/**
 * Inbound port for email-related application use cases.
 *
 * <p>
 * Defines the application-level contract for initiating and managing
 * email delivery operations within the system.
 * </p>
 *
 * <p>
 * This interface represents a <b>use-case boundary</b> in Clean Architecture.
 * It is the primary entry point for external actors (e.g., REST controllers,
 * schedulers, or other application services) to trigger email workflows.
 * </p>
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Coordinate the processing of email delivery requests</li>
 *   <li>Ensure reliable execution of email-related operations</li>
 *   <li>Support retry mechanisms for failed email deliveries</li>
 * </ul>
 *
 * <h3>Usage</h3>
 * <ul>
 *   <li>Consumed by application services and external entry points</li>
 *   <li>Should be injected via dependency inversion (never instantiate directly)</li>
 *   <li>Consumers should rely only on this contract, not its implementations</li>
 * </ul>
 *
 * <h3>Execution Semantics</h3>
 * <ul>
 *   <li>Operations are executed within an application-managed context</li>
 *   <li>Email processing may involve multiple steps (e.g., preparation, delivery, state handling)</li>
 * </ul>
 *
 * <h3>Failure Semantics</h3>
 * <ul>
 *   <li>Failures are propagated as {@link EmailApplicationException}</li>
 *   <li>No silent failures are allowed</li>
 *   <li>Consumers should treat operations as <b>potentially failing</b> and handle accordingly</li>
 * </ul>
 *
 *
 *  @see EmailSender
 *  @see EmailRepository
 *  @see TemplateRenderer
 */
public interface EmailService {

    /**
     * Initiates an email delivery operation based on the provided command and template variables.
     *
     * <h3>Contract</h3>
     * <ul>
     *   <li>Processes the provided email request</li>
     *   <li>Generates email content using the specified template and variables</li>
     *   <li>Attempts to deliver the email through the configured delivery mechanism</li>
     * </ul>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>{@code command} must not be {@code null}</li>
     *   <li>Template variables must satisfy the requirements of the referenced template</li>
     * </ul>
     *
     * <h3>Side Effects</h3>
     * <ul>
     *   <li>Triggers external communication with an email delivery system</li>
     *   <li>May result in internal state changes related to email processing</li>
     * </ul>
     *
     * <h3>Failure Handling</h3>
     * <ul>
     *   <li>Throws {@link EmailApplicationException} if processing or delivery fails</li>
     * </ul>
     *
     * @param command {@link EmailCreateCommand}  encapsulates email creation data
     * @param variables {@link Map(String, Object)} template variables used during content generation
     * @throws EmailApplicationException if email processing or delivery fails
     *
     * @see EmailSender#send(EmailMessage)
     * @see TemplateRenderer#render(String, Map)
     */
    void sendEmail(EmailCreateCommand command, Map<String, Object> variables);


    /**
     * Triggers retry processing for previously failed email delivery operations.
     *
     * <h3>Contract</h3>
     * <ul>
     *   <li>Identifies email operations eligible for retry</li>
     *   <li>Attempts re-delivery according to configured retry policies</li>
     * </ul>
     *
     * <h3>Constraints</h3>
     * <ul>
     *   <li>Retry eligibility is determined by system-defined policies (e.g., retry limits, timing constraints)</li>
     * </ul>
     *
     * <h3>Side Effects</h3>
     * <ul>
     *   <li>Triggers external email delivery attempts</li>
     *   <li>May update internal processing state</li>
     * </ul>
     *
     * <h3>Failure Handling</h3>
     * <ul>
     *   <li>Individual retry failures are handled internally</li>
     *   <li>Throws {@link EmailApplicationException} only in case of critical failure of the retry process</li>
     * </ul>
     *
     * @throws EmailApplicationException if the retry process fails critically
     *
     * @see EmailRepository#findRetryableEmails(LocalDateTime, int)
     * @see EmailSender#send(EmailMessage)
     */
    void retryFailedEmails();
}