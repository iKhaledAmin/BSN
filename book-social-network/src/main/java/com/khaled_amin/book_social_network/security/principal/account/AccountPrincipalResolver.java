package com.khaled_amin.book_social_network.security.principal.account;

import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.account.domain.repository.AccountRepository;
import com.khaled_amin.book_social_network.security.exception.InvalidTokenException;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import com.khaled_amin.book_social_network.security.jwt.JwtPayload;
import com.khaled_amin.book_social_network.security.principal.core.PrincipalResolver;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountPrincipalResolver implements PrincipalResolver {

    private final AccountRepository accountRepository;

    @Override
    public ActorType getType() {
        return ActorType.ACCOUNT;
    }

    @Override
    @Transactional
    public AuthenticatedPrincipal resolve(JwtPayload payload) {

        Account account = accountRepository
                .findByUsername(payload.getSubject())
                .orElseThrow(() -> InvalidTokenException.invalid()
                        .withDebug("reason", "Account not found")
                        .withDebug("subject", payload.getSubject()));

        return AccountPrincipal.of(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                account.getAccountStatus().isActive(),
                account.getAccountStatus().isLocked(),
                account.getRoleNames(),
                account.getAccountCode()
        );
    }
}