package com.khaled_amin.book_social_network.email.infrastructure.sender;

import com.khaled_amin.book_social_network.email.application.model.EmailMessage;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SmtpEmailSender extends AbstractEmailSenderAdapter {

    private final JavaMailSender mailSender;

    @Override
    protected void doSend(EmailMessage emailMessage) throws Exception {

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setFrom(emailMessage.from());
        helper.setTo(emailMessage.to());

        if (!emailMessage.cc().isEmpty()) {
            helper.setCc(emailMessage.cc().toArray(new String[0]));
        }

        if (!emailMessage.bcc().isEmpty()) {
            helper.setBcc(emailMessage.bcc().toArray(new String[0]));
        }

        if (emailMessage.replyTo() != null) {
            helper.setReplyTo(emailMessage.replyTo());
        }

        helper.setSubject(emailMessage.subject());
        helper.setText(emailMessage.body(), true);

        mailSender.send(message);
    }
}