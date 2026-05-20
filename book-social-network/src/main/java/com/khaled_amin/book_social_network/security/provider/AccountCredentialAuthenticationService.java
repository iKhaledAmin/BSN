package com.khaled_amin.book_social_network.security.provider;

import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.account.domain.repository.AccountRepository;
import com.khaled_amin.book_social_network.security.principal.account.AccountPrincipal;
import com.khaled_amin.book_social_network.security.exception.SecurityException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountCredentialAuthenticationService
        implements CredentialAuthenticationService <AccountPrincipal> {

    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountPrincipal authenticate(String username, String password) {

        Account account = accountRepository.findByUsername(username)
                .orElseThrow(SecurityException::invalidCredentials);

        if (!passwordEncoder.matches(password, account.getPassword())) {
            throw SecurityException.invalidCredentials();
        }

        if (!account.getAccountStatus().isActive()) {
            throw SecurityException.principalDisabled();
        }

        if (account.getAccountStatus().isLocked()) {
            throw SecurityException.principalLocked();
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