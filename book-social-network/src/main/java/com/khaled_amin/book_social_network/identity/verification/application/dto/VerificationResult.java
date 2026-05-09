package com.khaled_amin.book_social_network.identity.verification.application.dto;

import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.verification.domain.model.TokenType;

public record VerificationResult(
        ActorIdentity target,
        TokenType type
) {}