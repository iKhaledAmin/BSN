package com.khaled_amin.book_social_network.security;

import com.khaled_amin.book_social_network.user.application.actor.UserAccountActor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@AllArgsConstructor(access = AccessLevel.PRIVATE)
class SecurityUser implements UserDetails, Principal {

    @Getter
    private final Long id;

    private final String username;
    private final String password;
    private final boolean active;
    private final boolean locked;

    @Getter
    private final Set<String> roleNames;

    private final Set<GrantedAuthority> authorities;

    public static SecurityUser of(
            Long id,
            String username,
            String password,
            boolean active,
            boolean locked,
            Set<String> roleNames
    ) {
        Set<GrantedAuthority> authorities = roleNames
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                .collect(Collectors.toSet());

        return new SecurityUser(id, username, password, active, locked, roleNames, authorities);
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

    // -------------------- Principal --------------------

    @Override
    public String getName() {
        return username;
    }



    // -------------------- toActor --------------------
    public UserAccountActor toActor() {
        return new UserAccountActor(id, roleNames);
    }


}

