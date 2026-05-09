package com.khaled_amin.book_social_network.security.principal.clinet;

import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.identity.client.domain.model.Client;
import com.khaled_amin.book_social_network.identity.client.domain.repository.ClientRepository;
import com.khaled_amin.book_social_network.security.exception.InvalidTokenException;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import com.khaled_amin.book_social_network.security.jwt.JwtPayload;
import com.khaled_amin.book_social_network.security.principal.core.PrincipalResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClientPrincipalResolver implements PrincipalResolver {

    private final ClientRepository clientRepository;


    @Override
    public ActorType getType() {
        return ActorType.CLIENT;
    }

    @Override
    public AuthenticatedPrincipal resolve(JwtPayload payload) {

        Client client = clientRepository.findByClientId(payload.getSubject())
                .orElseThrow(() -> InvalidTokenException.invalid()
                        .withDebug("reason", "Client not found")
                        .withDebug("subject", payload.getSubject()));

        return  ClientPrincipal.of(
                client.getId(),
                client.getClientId(),
                client.getStatus().isActive(),
                client.getStatus().isLocked(),
                client.getScopes(),
                ActorType.CLIENT
        );
    }
}