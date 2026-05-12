package com.khaled_amin.book_social_network.security.principal.clinet;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class ClientPrincipal implements AuthenticatedPrincipal {

    @Getter
    private final Long dbId;

    private final String clientId;
    private final boolean active;
    private final boolean locked;
    private final ActorCode clientCode;

    @Getter
    private final Set<String> scopes;

    private final Set<GrantedAuthority> authorities;

    private ClientPrincipal(
            Long dbId,
            String clientId,
            boolean active,
            boolean locked,
            Set<String> scopes,
            Set<GrantedAuthority> authorities,
            ActorCode clientCode
    ) {
        this.dbId = dbId;
        this.clientId = clientId;
        this.active = active;
        this.locked = locked;
        this.scopes = scopes;
        this.authorities = authorities;
        this.clientCode = clientCode;
    }

    public static ClientPrincipal of(
            Long dbId,
            String clientId,
            boolean active,
            boolean locked,
            Set<String> scopes,
            ActorCode clientCode
    ) {

        Set<GrantedAuthority> authorities = scopes.stream()
                .map(scope -> new SimpleGrantedAuthority("SCOPE_" + scope))
                .collect(Collectors.toSet());

        return new ClientPrincipal(
                dbId,
                clientId,
                active,
                locked,
                scopes,
                authorities,
                clientCode
        );
    }

    @Override
    public String getSubject() {
        return clientId;
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

    @Override
    public ActorType getActorType() {
        return ActorType.CLIENT;
    }

    @Override
    public ActorCode getActorCode() {
        return clientCode;
    }


}
