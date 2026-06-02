package com.khaled_amin.book_social_network.email.application.model;

import com.khaled_amin.book_social_network.email.domain.model.Email;
import com.khaled_amin.book_social_network.email.exception.EmailTechnicalException;

import java.util.Set;

public record EmailMessage(
        String from,
        String to,
        Set<String> cc,
        Set<String> bcc,
        String replyTo,
        String subject,
        String body
) {

    public static EmailMessage from(Email email) {

        if (email == null) {
            throw EmailTechnicalException.nullEmail();
        }

        return new EmailMessage(
                email.getFrom(),
                email.getTo(),
                Set.copyOf(email.getCc()),
                Set.copyOf(email.getBcc()),
                email.getReplyTo(),
                email.getSubject(),
                email.getBody()
        );
    }
}