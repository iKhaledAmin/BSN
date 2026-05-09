package com.khaled_amin.book_social_network.auth.account.application.port.out;


import com.khaled_amin.book_social_network.security.principal.account.AccountPrincipal;

public interface AccountAuthenticationProvider {
    AccountPrincipal authenticate(String username, String password);
}
