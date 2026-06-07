package com.khaled_amin.book_social_network.security.provider;

import com.khaled_amin.book_social_network.identity.user.account.application.service.AccountService;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.security.exception.AuthenticationException;
import com.khaled_amin.book_social_network.security.exception.AuthorizationException;
import com.khaled_amin.book_social_network.security.principal.account.AccountPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountAuthenticationService implements CredentialAuthenticationService <AccountPrincipal> {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountPrincipal authenticate(String username, String password) {

        Account account = accountService.getOptionalByUsername(username)
                .orElseThrow(() -> AuthenticationException.invalidCredentials()
                        .withDebugDetails("reason", "Account not found")
                );

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw AuthenticationException.invalidCredentials()
                    .withDebugDetails("reason", "Invalid password");}

        if (account.getAccountStatus().isLocked()) {
            throw AuthenticationException.principalLocked("Account")
                    .withDebugDetails("reason", "Account is locked");
        }

        if (!account.getAccountStatus().isActive()) {
            throw AuthenticationException.principalInactive("Account")
                    .withDebugDetails("reason", "Account is inactive");
        }

        return AccountPrincipal.of(
                account.getUsername(),
                account.getAccountCode(),
                account.getAccountStatus().isActive(),
                account.getAccountStatus().isLocked(),
                account.getRoleNames(),
                account.getPermissions()
        );
    }
}