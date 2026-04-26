package com.khaled_amin.book_social_network.email.domain.command;

import com.khaled_amin.book_social_network.email.domain.value.*;
import java.util.Set;

public record EmailCreateCommand(
        From from,
        To to,
        ReplyTo replyTo,
        Set<String> cc,
        Set<String> bcc,
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
                From.of(from),
                To.of(to),
                ReplyTo.of(replyTo),
                cc,
                bcc,
                Subject.of(subject),
                Template.of(template)

        );

    }
}
