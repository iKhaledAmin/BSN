package com.khaled_amin.book_social_network.security.principal.account;

import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@RequiredArgsConstructor
public class AccountPrincipal implements AuthenticatedPrincipal {


    private final String username;
    private final ActorCode accountCode;

    private final boolean active;
    private final boolean locked;

    @Getter
    private final Set<String> roles;
    @Getter
    private final Set<String> permissions;

    private final Set<GrantedAuthority> authorities;




    public static AccountPrincipal of(
            String username, ActorCode accountCode,
            boolean active, boolean locked,
            Set<String> roles, Set<String> permissions
    ) {
        Set<GrantedAuthority> authorities = Stream.concat(
                        // Roles
                        roles.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role)),

                        // Permissions / Capabilities
                        permissions.stream().map(SimpleGrantedAuthority::new)
                )
                .collect(Collectors.toUnmodifiableSet());

        return new AccountPrincipal(username, accountCode, active, locked, roles, permissions, authorities);
    }


    @Override
    public String getSubject() {
        return username;
    }

    @Override
    public ActorCode getActorCode() {
        return accountCode;
    }

    @Override
    public ActorType getActorType() {
        return ActorType.ACCOUNT;
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

