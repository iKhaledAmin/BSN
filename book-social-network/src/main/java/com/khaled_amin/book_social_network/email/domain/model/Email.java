package com.khaled_amin.book_social_network.email.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.email.domain.command.EmailUpdateCommand;
import com.khaled_amin.book_social_network.email.domain.value.*;
import com.khaled_amin.book_social_network.email.exception.EmailBusinessException;
import com.khaled_amin.book_social_network.email.exception.EmailTechnicalException;
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
            comment = "Represent the last attempt to send the emailAddress"
    )
    private LocalDateTime lastAttemptAt;


    // -------------------------------------- Relationships ----------------------------------- //

    // TODO: Add attachments for emailAddress (future feature)
//    @Builder.Default
//    @OneToMany(mappedBy = "emailAddress")
//    private List<EmailAttachment> attachments = new ArrayList<>();

    // ------------------------------------ End Relationships -------------------------------- //

    // ------------------------------------- Business Methods ---------------------------------- //

    static Email create(
            EmailAddress from,
            EmailAddress to,
            EmailAddress replyTo,
            Set<EmailAddress> cc,
            Set<EmailAddress> bcc,
            Subject subject,
            Body body,
            Template template
    ) {

        return Email.builder()
                .from(from.value())
                .to(to.value())
                .replyTo(replyTo != null ? replyTo.value() : null)
                .cc(toValues(cc))
                .bcc(toValues(bcc))
                .subject(subject.value())
                .body(body.value())
                .template(template.value())
                .status(EmailStatus.getDefaultValue())
                .retryCount(0)
                .lastAttemptAt(LocalDateTime.now())
                .build();
    }

    public void update(EmailUpdateCommand command) {

        if (command == null) {
            throw EmailTechnicalException.nullUpdateCommand()
                    .withClientDetails("reason", "Update command cannot be null");
        }

        if (!this.status.isPending()) {
            throw EmailBusinessException.updateNotAllowed()
                    .withClientDetails("reason", "Email cannot be updated from current state");
        }

        command.subject().ifPresent(subject -> this.subject = subject.value());

        command.body().ifPresent(body -> this.body = body.value());

        command.replyTo().ifPresent(replyTo -> this.replyTo = replyTo.value());

        command.cc().ifPresent(cc -> this.cc = toValues(cc));

        command.bcc().ifPresent(bcc -> this.bcc = toValues(bcc));
    }

    public void markAsSent() {

        if (!(this.status.isPending() || this.status.isRetrying())) {
            throw EmailBusinessException.invalidTransition()
                    .withClientDetails("reason", "Email cannot be sent from current state");
        }

        this.status = EmailStatus.SENT;
        this.sentAt = LocalDateTime.now();
        this.lastAttemptAt = LocalDateTime.now();
    }

    public void markAsFailed(String error) {

        if (!(this.status.isPending() || this.status.isRetrying())) {
            throw EmailBusinessException.invalidTransition()
                    .withClientDetails("reason", "Email cannot be failed from current state");
        }

        if (error == null || error.isBlank()) {
            throw EmailTechnicalException.nullFailureReason();
        }

        this.status = EmailStatus.FAILED;
        this.errorMessage = error;
        this.lastAttemptAt = LocalDateTime.now();
    }

    public void markAsRetrying() {

        if (!this.status.isFailed()) {
            throw EmailBusinessException.invalidTransition()
                    .withClientDetails("reason", "Email can only be retried from FAILED state");
        }

        this.status = EmailStatus.RETRYING;
        this.retryCount++;
        this.lastAttemptAt = LocalDateTime.now();
    }



    // ------------------------------------ End Business Methods -------------------------------- //


    // ------------------------------------ Validation -------------------------------- //

    private static Set<String> toValues(Set<EmailAddress> emails) {

        if (emails == null) {
            return new HashSet<>();
        }

        return emails.stream()
                .map(EmailAddress::value)
                .collect(java.util.stream.Collectors.toSet());
    }
    // ------------------------------------ End Validation -------------------------------- //

}