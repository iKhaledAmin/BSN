package com.khaled_amin.book_social_network.security.jwt;


import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

@Getter
@AllArgsConstructor
public class JwtPayload {

     // Technical authentication identity
     private final String subject; // username OR clientId

     // Business actor identity
     private final ActorType actorType; // ACCOUNT | CLIENT
     private final ActorCode actorCode;  // accountCode OR clientCode

     // Time-based security
     private final Date issuedAt;
     private final Date expiration;

     // ACCOUNT
     private final Set<String> roles;
     private final Set<String> permissions;

     // CLIENT
     private final Set<String> scopes;
}