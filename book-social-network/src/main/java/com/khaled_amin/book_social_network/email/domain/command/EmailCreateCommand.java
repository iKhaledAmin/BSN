package com.khaled_amin.book_social_network.email.domain.command;

import com.khaled_amin.book_social_network.email.domain.value.EmailAddress;
import com.khaled_amin.book_social_network.email.domain.value.Subject;
import com.khaled_amin.book_social_network.email.domain.value.Template;

import java.util.Set;
import java.util.stream.Collectors;

public record EmailCreateCommand(
        EmailAddress from,
        EmailAddress to,
        EmailAddress replyTo,
        Set<EmailAddress> cc,
        Set<EmailAddress> bcc,
        Subject subject,
        Template template
) {

    public static EmailCreateCommand of(
            String from,
            String to,
            String replyTo,
            Set<String> cc,
            Set<String> bcc,
            String subject,
            String template
    ) {
        return new EmailCreateCommand(
                EmailAddress.of(from),
                EmailAddress.of(to),
                replyTo == null ? null : EmailAddress.of(replyTo),
                mapEmails(cc),
                mapEmails(bcc),
                Subject.of(subject),
                Template.of(template)
        );
    }

    private static Set<EmailAddress> mapEmails(Set<String> emails) {
        if (emails == null) {
            return null;
        }

        return emails.stream()
                .map(EmailAddress::of)
                .collect(Collectors.toSet());
    }
}