package com.khaled_amin.book_social_network.email.domain.model;

import com.khaled_amin.book_social_network.email.domain.command.EmailCreateCommand;
import com.khaled_amin.book_social_network.email.domain.value.Body;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailFactory {

    public Email create(EmailCreateCommand command, String body) {

        // TODO: Add attachments for emailAddress (future feature)
        // Attachment attachments = createAttachments(command);

        return Email.create(
                command.from(),
                command.to(),
                command.replyTo(),
                command.cc(),
                command.bcc(),
                command.subject(),
                Body.of(body),
                command.template()
        );
    }
}
