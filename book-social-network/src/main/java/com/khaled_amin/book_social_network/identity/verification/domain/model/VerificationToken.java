package com.khaled_amin.book_social_network.identity.verification.domain.model;

import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.verification.exception.VerificationException;
import jakarta.persistence.*;
import lombok.*;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "verification_tokens")
public class VerificationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    // -------------------- Token --------------------

    @Column(nullable = false, unique = true)
    private String code;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type", nullable = false,updatable = false)
    private TokenType tokenType;

    // -------------------- Target --------------------

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(
                    name = "actorType",
                    column = @Column(
                            name = "target_actor_type",
                            nullable = false,
                            updatable = false
                    )
            ),
            @AttributeOverride(
                    name = "actorCode.value",
                    column = @Column(
                            name = "target_actor_code",
                            nullable = false,
                            updatable = false
                    )
            )
    })
    private ActorIdentity target;

    // -------------------- Lifecycle --------------------

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "expired_at", nullable = false)
    private LocalDateTime expiredAt;

    @Column(name = "verified_at")
    private LocalDateTime VerifiedAt;

    // -------------------- Factory --------------------

    public static VerificationToken create(
            TokenType type,
            ActorIdentity target,
            int codeLength,
            int expirationMinutes
    ) {

        String code = generateCode(codeLength);
        LocalDateTime expiredAt = LocalDateTime.now().plusMinutes(expirationMinutes);

        return VerificationToken.builder()
                .code(code)
                .tokenType(type)
                .target(target)
                .createdAt(LocalDateTime.now())
                .expiredAt(expiredAt)
                .build();
    }

    // -------------------- Business Methods --------------------

    public void verify() {

        if (isVerified()) {
            throw VerificationException.alreadyVerified()
                    .withDebugDetails("VerifiedAt",VerifiedAt);
        }

        if (isExpired()) {
            throw VerificationException.expired()
                    .withDebugDetails("expiredAt",expiredAt);
        }

        this.VerifiedAt = LocalDateTime.now();
    }

    public void canBeUsedFor(TokenType expectedType) {
        if(!this.tokenType.same(expectedType)){
            throw VerificationException.invalidToken()
                    .withDebugDetails("reason","Wrong token type")
                    .withDebugDetails("ActualTokenType",this.tokenType.name())
                    .withDebugDetails("providedTokenType",expectedType.name());
        }
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiredAt);
    }

    public boolean isVerified() {
        return VerifiedAt != null;
    }


    // -------------------- Helper --------------------

    private static String generateCode(int length) {
        String digits = "0123456789";
        SecureRandom random = new SecureRandom();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < length; i++) {
            sb.append(digits.charAt(random.nextInt(digits.length())));
        }
        return sb.toString();
    }
}