package com.khaled_amin.book_social_network.core.audit;

import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.provider.ActorProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditAwareImpl implements AuditorAware<String> {

    private final ActorProvider actorProvider;

    @Override
    public Optional<String> getCurrentAuditor() {

        // Get the current account from the account provider
        Actor actor = actorProvider.getCurrent();

        // e.g. ACCOUNT:123 or SYSTEM:SYSTEM or SERVICE:service_name or ANONYMOUS:ANONYMOUS
        return Optional.of(actor.getType() + ":" + actor.getActorIdentity().id());
    }
}

