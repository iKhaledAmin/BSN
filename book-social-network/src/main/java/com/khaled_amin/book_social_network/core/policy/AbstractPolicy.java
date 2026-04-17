package com.khaled_amin.book_social_network.core.policy;

import com.khaled_amin.book_social_network.core.actor.Actor;

public abstract class AbstractPolicy<C extends BasePolicyContext> implements BasePolicy<C> {


    protected final void allow() {}


    @Override
    public final void check(C context) {

        if (context == null)
            throw PolicyException.invalidPolicyContext()
                    .withDetail("reason", "Context cannot be null")
                    .withDetail("operationName",getOperationName());

        validateContext(context);


        Actor actor = extractActor(context);
        if (actor == null)
            throw PolicyException.invalidPolicyContext()
                    .withDetail("reason", "Actor cannot be null")
                    .withDetail("operationName",getOperationName());



        switch (actor.getType()) {
            case USER_ACCOUNT -> handleUser(context);
            case ANONYMOUS -> handleAnonymous(context);
            case SYSTEM -> handleSystem(context);
            case SERVICE -> handleService(context);
            default -> deny("Unsupported actor type");
        }
    }


    protected abstract Actor extractActor(C context);
    protected abstract void deny(String reason);
    protected abstract String getOperationName();

    protected void handleSystem(C context) {
        deny("SYSTEM not allowed to " + getOperationName());
    }
    protected void handleAnonymous(C context) {
        deny("ANONYMOUS not allowed to " + getOperationName());
    }

    protected void handleService(C context) {
        deny("SERVICE not allowed to " + getOperationName());
    }

    protected void handleUser(C context) {
        deny("USER_ACCOUNT not allowed to " + getOperationName());
    }
}