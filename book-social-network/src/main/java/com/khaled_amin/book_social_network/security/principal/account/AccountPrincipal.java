package com.khaled_amin.book_social_network.security.principal.account;

import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@RequiredArgsConstructor
public class AccountPrincipal implements UserDetails, Principal, AuthenticatedPrincipal {

    @Getter
    private final Long id;

    private final String username;
    private final String password;
    private final boolean active;
    private final boolean locked;

    @Getter
    private final Set<String> roleNames;
    private final Set<GrantedAuthority> authorities;
    private final ActorCode accountCode;


    public static AccountPrincipal of(
            Long id,
            String username,
            String password,
            boolean active,
            boolean locked,
            Set<String> roleNames,
            ActorCode accountCode
    ) {
        Set<GrantedAuthority> authorities = roleNames
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());

        return new AccountPrincipal(id, username, password, active, locked, roleNames, authorities, accountCode);
    }




    // -------------------- UserDetails --------------------

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    // ------------------- Principal -------------------

    @Override
    public String getName() {
        return username;
    }

    // -------------------- AuthenticatedPrincipal --------------------

    @Override
    public String getSubject() {
        return username;
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
    public ActorType getActorType() {
        return ActorType.ACCOUNT;
    }

    @Override
    public ActorCode getActorCode() {
        return accountCode;
    }


}

