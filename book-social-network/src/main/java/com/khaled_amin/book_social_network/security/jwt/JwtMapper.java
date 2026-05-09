package com.khaled_amin.book_social_network.security.jwt;


import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class JwtMapper {
    private final JwtService jwtService;

    public JwtResponse toResponse(String token) {

        return JwtResponse.builder()
                .accessToken(token)
                .type("Bearer")
                .expiresAt(jwtService.extractExpiration(token).getTime())
                .build();
    }
}
