package com.khaled_amin.book_social_network.email.infrastructure.scheduler;

import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;
import com.khaled_amin.book_social_network.core.logging.audit.ExceptionLogger;
import com.khaled_amin.book_social_network.email.application.port.in.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class EmailRetryScheduler {
    private final EmailService emailService;
    private final ExceptionLogger exceptionLogger;

    @Scheduled(fixedDelayString = "#{${application.email.retry.scheduler.interval-seconds} * 1000}") // Run every interval seconds
    public void retryFailedEmails() {

        // todo later we will create operational event logger to log operational events like this
        // log the start and end of the retry process and what success or failure

        try {
            emailService.retryFailedEmails();
        } catch (TechnicalException ex) {
            exceptionLogger.log(ex);
        }

    }
}