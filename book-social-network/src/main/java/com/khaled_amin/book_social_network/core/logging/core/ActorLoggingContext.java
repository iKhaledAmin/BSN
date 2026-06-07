package com.khaled_amin.book_social_network.core.logging.core;

import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import org.slf4j.MDC;

public final class ActorLoggingContext {

    private ActorLoggingContext() {}

    public static void put(AuthenticatedPrincipal principal) {

        MDC.put(
                LoggingConstants.ACTOR_TYPE,
                principal.getActorType().name()
        );

        MDC.put(
                LoggingConstants.ACTOR_CODE,
                principal.getActorCode().toString()
        );


    }

    public static void clear() {

        MDC.remove(LoggingConstants.ACTOR_TYPE);
        MDC.remove(LoggingConstants.ACTOR_CODE);

    }
}