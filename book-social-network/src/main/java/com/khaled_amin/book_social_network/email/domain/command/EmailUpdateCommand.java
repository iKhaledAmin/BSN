package com.khaled_amin.book_social_network.email.domain.command;

import com.khaled_amin.book_social_network.email.domain.value.Body;
import com.khaled_amin.book_social_network.email.domain.value.EmailAddress;
import com.khaled_amin.book_social_network.email.domain.value.Subject;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public record EmailUpdateCommand(
        Optional<Subject> subject,
        Optional<Body> body,
        Optional<Set<EmailAddress>> cc,
        Optional<Set<EmailAddress>> bcc,
        Optional<EmailAddress> replyTo
) {

    public static EmailUpdateCommand of(
            String subject,
            String body,
            Set<String> cc,
            Set<String> bcc,
            String replyTo
    ) {
        return new EmailUpdateCommand(
                Optional.ofNullable(subject).map(Subject::of),
                Optional.ofNullable(body).map(Body::of),
                Optional.ofNullable(cc).map(EmailUpdateCommand::mapEmails),
                Optional.ofNullable(bcc).map(EmailUpdateCommand::mapEmails),
                Optional.ofNullable(replyTo).map(EmailAddress::of)
        );
    }

    private static Set<EmailAddress> mapEmails(Set<String> emails) {
        return emails.stream()
                .map(EmailAddress::of)
                .collect(Collectors.toSet());
    }
}