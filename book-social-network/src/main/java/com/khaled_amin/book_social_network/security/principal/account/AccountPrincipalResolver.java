package com.khaled_amin.book_social_network.security.principal.account;

import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.account.domain.repository.AccountRepository;
import com.khaled_amin.book_social_network.security.exception.AuthenticationException;
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

        Account account = accountRepository.findByUsername(payload.getSubject())
                .orElseThrow(() -> AuthenticationException.principalNotFound("Account not found")
                        .withDebugDetails("subject", payload.getSubject())
                        .withDebugDetails("actorCode", payload.getActorCode())
                        .withDebugDetails("actorType", payload.getActorType().name()));

        return AccountPrincipal.of(
                payload.getSubject(),
                payload.getActorCode(),

                account.getAccountStatus().isActive(),
                account.getAccountStatus().isLocked(),

                payload.getRoles(),
                payload.getPermissions()
        );
    }
}