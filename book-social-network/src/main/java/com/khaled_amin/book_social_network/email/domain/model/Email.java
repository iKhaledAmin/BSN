package com.khaled_amin.book_social_network.email.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.email.domain.command.EmailUpdateCommand;
import com.khaled_amin.book_social_network.email.domain.exception.EmailDomainException;
import com.khaled_amin.book_social_network.email.domain.value.*;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "emails")
public class Email extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_id")
    private Long id;

    // -------------------- Sender --------------------

    @Column(name = "from_address", nullable = false, updatable = false)
    private String from;

    // -------------------- Recipient --------------------


    @Column(name = "to_address", nullable = false, updatable = false)
    private String to;


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "email_cc_recipients",
            joinColumns = @JoinColumn(name = "email_id")
    )
    @Builder.Default
    @Column(name = "recipient_address", nullable = false)
    private Set<String> cc = new HashSet<>(); // Carbon Copy


    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "email_bcc_recipients",
            joinColumns = @JoinColumn(name = "email_id")
    )
    @Builder.Default
    @Column(name = "recipient_address", nullable = false)
    private Set<String> bcc = new HashSet<>(); // Blind Carbon Copy

    // -------------------- Reply To --------------------

    @Column(name = "reply_to_address")
    private String replyTo;

    // -------------------- Content --------------------
    @Column(name = "subject", nullable = false)
    private String subject;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(name = "body", nullable = false ,columnDefinition = "MEDIUMTEXT" )
    private String body;

    @Column(name = "template_name", nullable = false, updatable = false)
    private String template;




    // -------------------- Status --------------------
    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "email_status", nullable = false)
    private EmailStatus status = EmailStatus.getDefaultValue();

    @Column(name = "error_message")
    private String errorMessage;

    // -------------------- Retry --------------------
    @Column(name = "retry_count")
    private int retryCount = 0;

    // -------------------- Timestamps --------------------

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(
            name = "last_attempt_at",
            comment = "Represent the last attempt to send the email"
    )
    private LocalDateTime lastAttemptAt;


    // -------------------------------------- Relationships ----------------------------------- //

    // TODO: Add attachments for email (future feature)
//    @Builder.Default
//    @OneToMany(mappedBy = "email")
//    private List<EmailAttachment> attachments = new ArrayList<>();

    // ------------------------------------ End Relationships -------------------------------- //

    // ------------------------------------- Business Methods ---------------------------------- //

    static Email create(
            From from,
            To to,
            ReplyTo replyTo,
            Set<String> cc,
            Set<String> bcc,
            Subject subject,
            Body body,
            Template template

    ) {
        validateEmails(cc);
        validateEmails(bcc);

        Email newEmail = Email.builder()
                .from(from.value())
                .to(to.value())
                .replyTo(replyTo.value())
                .cc(cc != null ? cc : new HashSet<>())
                .bcc(bcc != null ? bcc : new HashSet<>())
                .subject(subject.value())
                .body(body.value())
                .template(template.value())
                .status(EmailStatus.getDefaultValue())
                .retryCount(0)
                .lastAttemptAt(LocalDateTime.now())
                .build();

        newEmail.validateState();

        return newEmail;
    }

    public void update(EmailUpdateCommand command) {

        if (command == null) {
            throw EmailDomainException.invalidUpdateCommand().withDetail("reason", "Update command cannot be null");
        }

        if (!this.status.isPending()) {
            throw EmailDomainException.updateViolation().withDetail("reason", "Email is not in pending stage");
        }

        command.subject().ifPresent(s -> this.subject = s.value());
        command.body().ifPresent(b -> this.body = b.value());
        command.replyTo().ifPresent(replyTo -> this.replyTo = replyTo.value());
        command.cc().ifPresent(c -> {
            validateEmails(c);
            this.cc = c;
        });
        command.bcc().ifPresent(b -> {
            validateEmails(b);
            this.bcc = b;
        });


        this.validateState();
    }

    public void markAsSent() {

        if (!(this.status.isPending() || this.status.isRetrying())) {
            throw EmailDomainException.invalidedTransition()
                    .withDetail("reason", "Email can not be sent ,it is not in pending or retrying stage");
        }

        this.status = EmailStatus.SENT;
        this.sentAt = LocalDateTime.now();
        this.lastAttemptAt = LocalDateTime.now();
    }

    public void markAsFailed(String error) {

        if (!(this.status.isPending() || this.status.isRetrying())) {
            throw EmailDomainException.invalidedTransition()
                    .withDetail("reason", "Email can not be failed ,it is not in pending or retrying stage");
        }

        if (error == null || error.isBlank()) {
            throw EmailDomainException.invalidFailureReason()
                    .withDetail("reason", "Error message cannot be null or empty");
        }

        this.status = EmailStatus.FAILED;
        this.errorMessage = error;
        this.lastAttemptAt = LocalDateTime.now();
    }

    public void markAsRetrying() {

        if (!this.status.isFailed()) {
            throw EmailDomainException.invalidedTransition()
                    .withDetail("reason", "Email can not be retried ,it is not in failed stage");
        }

        this.status = EmailStatus.RETRYING;
        this.retryCount++;
        this.lastAttemptAt = LocalDateTime.now();
    }



    // ------------------------------------ End Business Methods -------------------------------- //


    // ------------------------------------ Validation -------------------------------- //
    private void validateState() {

        if (from == null || from.isBlank()) {
            throw EmailDomainException.invalidState()
                    .withDetail("reason", "From email address must not be null or empty");
        }

        if (to == null || to.isBlank()) {
            throw EmailDomainException.invalidState()
                    .withDetail("reason", "To email address must not be null or empty");
        }

        if (subject == null || subject.isBlank()) {
            throw EmailDomainException.invalidState()
                    .withDetail("reason", "Subject of email must not be empty");
        }

        if (body == null || body.isBlank()) {
            throw EmailDomainException.invalidState()
                    .withDetail("reason", "Body of email must not be empty");
        }

        if (template == null || template.isBlank()) {
            throw EmailDomainException.invalidState()
                    .withDetail("reason", "Template of email must not be empty");
        }

        if (status == null) {
            throw EmailDomainException.invalidState()
                    .withDetail("reason", "Status of email must not be null");
        }

        if (retryCount < 0) {
            throw EmailDomainException.invalidState()
                    .withDetail("reason", "Retry count of email must not be negative");
        }

        if (status.isFailed() && ( errorMessage == null || errorMessage.isBlank())) {
            throw EmailDomainException.invalidState()
                    .withDetail("reason", "Error message of failed email must not be null or empty");
        }
    }

    private static void validateEmails(Set<String> emails) {
        if (emails == null) return;

        if (emails.isEmpty()) return;

        for (String email : emails) {
            if (email == null || !email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                throw EmailDomainException.invalidEmailAddress()
                        .withDetail("reason", "Provided email address not valid email format")
                        .withDetail("invalidEmail", email);
            }
        }
    }
    // ------------------------------------ End Validation -------------------------------- //

}