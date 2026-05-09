package com.khaled_amin.book_social_network.security.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class JwtResponse {

    @JsonProperty("access_token")
    private String accessToken;
    private String type; // "Bearer"
    @JsonProperty("expires_at")
    private long expiresAt;
}
