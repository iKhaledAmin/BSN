package com.khaled_amin.book_social_network.email.infrastructure.persistence;

import com.khaled_amin.book_social_network.core.persistence.BaseRepository;

import com.khaled_amin.book_social_network.email.domain.model.Email;
import com.khaled_amin.book_social_network.email.domain.model.EmailStatus;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface EmailJpaRepository extends BaseRepository<Email, Long> {


//    List<EmailAddress> findAllByStatusInAndRetryCountLessThanAndLastAttemptAtBefore(
//            List<EmailStatus> emailStatuses,
//            int maxAttempts,
//            LocalDateTime threshold
//    );

    @Query("""
    SELECT DISTINCT e FROM Email e
    LEFT JOIN FETCH e.cc
    LEFT JOIN FETCH e.bcc
    WHERE e.status IN :statuses
    AND e.retryCount < :maxAttempts
    AND e.lastAttemptAt < :threshold
    """)
    List<Email> findRetryableEmailsWithRecipients(
            List<EmailStatus> statuses,
            int maxAttempts,
            LocalDateTime threshold
    );
}
