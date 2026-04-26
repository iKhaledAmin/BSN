package com.khaled_amin.book_social_network.email.infrastructure.persistence;

import com.khaled_amin.book_social_network.email.domain.model.Email;
import com.khaled_amin.book_social_network.email.domain.model.EmailStatus;
import com.khaled_amin.book_social_network.email.application.port.out.EmailRepository;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Repository
public class EmailRepositoryImpl implements EmailRepository {

    private final EmailJpaRepository emailJpaRepository;

    @Override
    public Email save(Email email) {
        return emailJpaRepository.save(email);
    }


    @Override
    public List<Email> findRetryableEmails(LocalDateTime threshold, int maxAttempts) {
        return emailJpaRepository
                .findRetryableEmailsWithRecipients(
                        List.of(EmailStatus.FAILED, EmailStatus.RETRYING),
                        maxAttempts,
                        threshold
                );
    }
}
