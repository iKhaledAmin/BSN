package com.khaled_amin.book_social_network.email.application.model;

import com.khaled_amin.book_social_network.email.domain.model.Email;

import java.util.*;

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
            throw new IllegalArgumentException("Email must not be null");
        }

        return new EmailMessage(
                email.getFrom(),
                email.getTo(),
                email.getCc(),
                email.getBcc(),
                email.getReplyTo(),
                email.getSubject(),
                email.getBody()
        );
    }

}