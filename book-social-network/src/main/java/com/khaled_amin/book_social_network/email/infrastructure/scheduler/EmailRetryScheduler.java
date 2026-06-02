package com.khaled_amin.book_social_network.email.infrastructure.scheduler;

import com.khaled_amin.book_social_network.email.application.port.in.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class EmailRetryScheduler {
    private final EmailService emailService;

    @Scheduled(fixedDelayString = "#{${application.email.retry.scheduler.interval-seconds} * 1000}") // Run every interval seconds
    public void retryFailedEmails() {

        try {
            emailService.retryFailedEmails();
        } catch (RuntimeException ex) {
            // todo: log
           // log.error("EmailAddress retry job failed", ex);
        }

    }
}