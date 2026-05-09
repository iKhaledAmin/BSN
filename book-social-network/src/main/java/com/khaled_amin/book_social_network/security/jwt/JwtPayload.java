package com.khaled_amin.book_social_network.security.jwt;


import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

@Getter
@AllArgsConstructor
public class JwtPayload {

     // ActorSource
     private final String subject;     // username OR clientId
     private final ActorType actorType;   // ACCOUNT | CLIENT

     // Time-based security
     private final Date issuedAt;
     private final Date expiration;

     // Authorization (roles for account or scopes for client)
     private final Set<String> authorities;
}