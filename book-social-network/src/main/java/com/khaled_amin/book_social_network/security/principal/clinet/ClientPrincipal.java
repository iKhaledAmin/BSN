package com.khaled_amin.book_social_network.security.principal.clinet;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@AllArgsConstructor
public class ClientPrincipal implements AuthenticatedPrincipal {


    private final String clientId;
    private final ActorCode actorCode;

    private final boolean active;
    private final boolean locked;

    @Getter
    private final Set<String> scopes;
    private final Set<GrantedAuthority> authorities;


    public static ClientPrincipal of(
            String clientId, ActorCode clientCode,
            boolean active, boolean locked,
            Set<String> scopes
    ) {

        Set<GrantedAuthority> authorities = scopes
                .stream()
                .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                .collect(Collectors.toUnmodifiableSet());

        return new ClientPrincipal(clientId, clientCode, active, locked, scopes, authorities);
    }

    @Override
    public String getSubject() {
        return clientId;
    }

    @Override
    public ActorCode getActorCode() {
        return actorCode;
    }

    @Override
    public ActorType getActorType() {
        return ActorType.CLIENT;
    }


    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public boolean isLocked() {
        return locked;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


}
