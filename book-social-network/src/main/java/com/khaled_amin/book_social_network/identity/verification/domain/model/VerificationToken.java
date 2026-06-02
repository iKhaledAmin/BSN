package com.khaled_amin.book_social_network.identity.verification.domain.model;

import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.verification.domain.exception.VerificationDomainException;
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
    //@Column(name = "target", nullable = false,updatable = false)
    private ActorIdentity target;

    // -------------------- Lifecycle --------------------

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime validatedAt;

    // -------------------- Factory --------------------

    public static VerificationToken create(
            TokenType type,
            ActorIdentity target,
            int codeLength,
            int expirationMinutes
    ) {

        String code =generateCode(codeLength);
        LocalDateTime expiresAt = LocalDateTime.now().plusMinutes(expirationMinutes);

        VerificationToken token = VerificationToken.builder()
                .code(code)
                .tokenType(type)
                .target(target)
                .createdAt(LocalDateTime.now())
                .expiresAt(expiresAt)
                .build();

        token.validateState();

        return token;
    }

    // -------------------- Business Methods --------------------

    public void validate() {

        if (isValidated()) {
            throw VerificationDomainException.alreadyUsed();
        }

        if (isExpired()) {
            throw VerificationDomainException.expired();
        }

        this.validatedAt = LocalDateTime.now();
    }

    public void canBeUsedFor(TokenType expectedType) {
        if(!this.tokenType.same(expectedType)){
            throw VerificationDomainException.invalidToken()
                    .withClientDetails("reason","Wrong token type");
        }
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiresAt);
    }

    public boolean isValidated() {
        return validatedAt != null;
    }

    // -------------------- Validation --------------------

    private void validateState() {

        if (code == null || code.isBlank()) {
            throw VerificationDomainException.invalidState()
                    .withClientDetails("reason", "Token code must not be null");
        }

        if (tokenType == null) {
            throw VerificationDomainException.invalidState()
                    .withClientDetails("reason", "Token type must not be null");
        }

        if (target == null) {
            throw VerificationDomainException.invalidState()
                    .withClientDetails("reason", "Target account must not be null");
        }

        if (expiresAt == null || createdAt == null) {
            throw VerificationDomainException.invalidState()
                    .withClientDetails("reason", "Timestamps must not be null");
        }

        if (expiresAt.isBefore(createdAt)) {
            throw VerificationDomainException.invalidState()
                    .withClientDetails("reason", "Expiration must be after creation");
        }
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