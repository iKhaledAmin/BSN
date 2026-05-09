package com.khaled_amin.book_social_network.email.application.service;

import com.khaled_amin.book_social_network.email.application.exception.EmailApplicationException;
import com.khaled_amin.book_social_network.email.infrastructure.config.EmailProperties;
import com.khaled_amin.book_social_network.email.application.model.EmailMessage;
import com.khaled_amin.book_social_network.email.application.port.in.EmailService;
import com.khaled_amin.book_social_network.email.application.port.out.EmailRepository;
import com.khaled_amin.book_social_network.email.application.port.out.EmailSender;
import com.khaled_amin.book_social_network.email.application.port.out.TemplateRenderer;
import com.khaled_amin.book_social_network.email.domain.command.EmailCreateCommand;
import com.khaled_amin.book_social_network.email.domain.model.Email;
import com.khaled_amin.book_social_network.email.domain.model.EmailFactory;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
 * Default implementation of {@link EmailService}.
 *
 * <p>
 * Orchestrates the full email delivery workflow including:
 * template rendering, domain entity creation, persistence,
 * delivery attempts, and retry handling.
 * </p>
 *
 * <h3>Workflow Overview</h3>
 * <ul>
 *   <li>Render email content from template</li>
 *   <li>Create domain email entity</li>
 *   <li>Persist initial state</li>
 *   <li>Attempt delivery via {@link EmailSender}</li>
 *   <li>Update state based on result</li>
 * </ul>
 *
 * <h3>Retry Strategy</h3>
 * <ul>
 *   <li>Retries are executed based on configured policy</li>
 *   <li>Only eligible emails are selected for retry</li>
 *   <li>Each retry updates the email state accordingly</li>
 * </ul>
 *
 * <h3>Design Notes</h3>
 * <ul>
 *   <li>Follows orchestration pattern (no business logic leakage)</li>
 *   <li>Delegates domain rules to {@link  Email} aggregate</li>
 *   <li>Delegates rendering to {@link TemplateRenderer}</li>
 *   <li>Delegates delivery to {@link EmailSender}</li>
 *   <li>Delegates persistence and Retrieval to {@link EmailRepository}</li>
 * </ul>
 */

@AllArgsConstructor
@Service
public class EmailServiceImpl implements EmailService {

    private final TemplateRenderer templateRenderer;
    private final EmailRepository emailRepository;
    private final EmailSender emailSender;

    private final EmailFactory emailFactory;
    private final EmailProperties emailProperties;


    @Override
    public void sendEmail(EmailCreateCommand command, Map<String, Object> variables) {

        // Render template → body
        String renderedBody ;
        try {
            renderedBody = templateRenderer.render(command.template().value(), variables);
        } catch (Exception ex) {
            // Template rendering failed
            // todo: log the failure
            throw EmailApplicationException.renderFailed(ex);
        }

        // Create Email
        Email email = emailFactory.create(command, renderedBody);

        // Persist initial state (PENDING)
        emailRepository.save(email);

        // Try sending
        try {
            EmailMessage emailMessage = EmailMessage.from(email);
            emailSender.send(emailMessage);

            email.markAsSent();
            emailRepository.save(email); // Persist sent state

        } catch (Exception ex) {

            email.markAsFailed(ex.getMessage());
            emailRepository.save(email); // Persist failed state

            // todo: log the failure
            throw EmailApplicationException.sendFailed(ex);
        }

    }


    @Override
    public void retryFailedEmails() {

        // Calculate threshold
        LocalDateTime threshold = LocalDateTime.now().minusSeconds(emailProperties.retry().policy().backoffSeconds());

        // Fetch FAILED emails
        List<Email> failedEmails = emailRepository.findRetryableEmails(
                threshold,
                emailProperties.retry().policy().maxAttempts()
        );



        // Iterate
        for (Email email : failedEmails) {
            retrySingleEmail(email);
        }
    }



    private void retrySingleEmail(Email email) {

        // Already sent or pending do not retry
        if (email.getStatus().isSent() || email.getStatus().isPending() ) {
            return;
        }

        //  Mark retrying
        email.markAsRetrying();
        emailRepository.save(email); // persist RETRYING state

        try {
            // Try sending again
            EmailMessage emailMessage = EmailMessage.from(email);
            emailSender.send(emailMessage);

            // Success
            email.markAsSent();
            emailRepository.save(email); // persist SENT state

        } catch (Exception ex) {
            // Failure again
            email.markAsFailed(ex.getMessage());
            emailRepository.save(email); // persist FAILED state

            throw EmailApplicationException.sendFailed(ex);
        }

    }

}
