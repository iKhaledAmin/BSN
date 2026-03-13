package com.khaled_amin.book_social_network.security;

import com.khaled_amin.book_social_network.user.model.entity.Account;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentUserServiceImpl implements CurrentUserService{

    public Account getCurrentAccount() {
        return getAuthenticationAccount();
    }

    public Long getCurrentAccountId() {
        return getAuthenticationAccount().getId();
    }

    public String getCurrentUsername() {
        return getAuthenticationAccount().getUsername();
    }

    private Account getAuthenticationAccount() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !(authentication.getPrincipal() instanceof Account account)) {
            throw new AuthenticationCredentialsNotFoundException("No authenticated account found");
        }

        return account;
    }
}