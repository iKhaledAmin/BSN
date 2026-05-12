package com.khaled_amin.book_social_network.book.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "book_transactions")
public class BookTransaction extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "transaction_id")
    private Long id;
    private boolean returned;
    private boolean returnApproved;


    // ---------------------------------------- Relation ----------------------------------- //

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;


    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private Book book;

    // --------------------------------------- End Relation -------------------------------- //



}
