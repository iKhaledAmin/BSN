package com.khaled_amin.book_social_network.core.audit;

import com.khaled_amin.book_social_network.core.actor.Actor;
import com.khaled_amin.book_social_network.core.actor.ActorProvider;
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
        // Get the current actor from the actor provider
        Actor actor = actorProvider.getCurrentActor();
        // e.g. USER_ACCOUNT:123 or SYSTEM:SYSTEM or SERVICE:service_name or ANONYMOUS:ANONYMOUS
        return Optional.of(actor.getType() + ":" + actor.getIdentity().id());
    }
}

