package com.Khaled_Amin.book_social_network.user.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tokens")
public class Token {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(name = "token")
    private String token;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "validated_at")
    private LocalDateTime validatedAt;


    // -------------------------------------- Relationships ----------------------------------- //
    @ManyToOne(fetch = FetchType.LAZY,optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    // ------------------------------------ End Relationships -------------------------------- //
}
