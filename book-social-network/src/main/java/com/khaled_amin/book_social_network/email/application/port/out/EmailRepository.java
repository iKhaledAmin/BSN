package com.khaled_amin.book_social_network.email.application.port.out;

import com.khaled_amin.book_social_network.email.domain.model.Email;

import java.time.LocalDateTime;
import java.util.List;


public interface EmailRepository {


    Email save(Email email);

    List<Email> findRetryableEmails(LocalDateTime retryThreshold, int maxAttempts);
}