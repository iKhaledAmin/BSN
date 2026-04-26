package com.khaled_amin.book_social_network.email.domain.command;

import com.khaled_amin.book_social_network.email.domain.value.Body;
import com.khaled_amin.book_social_network.email.domain.value.ReplyTo;
import com.khaled_amin.book_social_network.email.domain.value.Subject;

import java.util.Optional;
import java.util.Set;

public record EmailUpdateCommand(
        Optional<Subject> subject,
        Optional<Body> body,
        Optional<Set<String>> cc,
        Optional<Set<String>> bcc,
        Optional<ReplyTo> replyTo
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
                Optional.ofNullable(cc),
                Optional.ofNullable(bcc),
                Optional.ofNullable(replyTo).map(ReplyTo::of)
        );
    }
}