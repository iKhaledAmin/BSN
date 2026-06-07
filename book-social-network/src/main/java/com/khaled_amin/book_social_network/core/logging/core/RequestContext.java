package com.khaled_amin.book_social_network.core.logging.core;

import org.slf4j.MDC;

public final class RequestContext {

    private RequestContext() {}

    public static String getRequestId() {
        return MDC.get(LoggingConstants.REQUEST_ID);
    }

    public static String getActorType() {
        return MDC.get(LoggingConstants.ACTOR_TYPE);
    }

    public static String getActorCode() {
        return MDC.get(LoggingConstants.ACTOR_CODE);
    }


}