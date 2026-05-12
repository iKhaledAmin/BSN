package com.khaled_amin.book_social_network.identity.core.model;

/**
 * Represents an internal system actor.
 *
 * <p>
 * Used for scheduled jobs, internal workflows,
 * infrastructure operations, and background tasks.
 * </p>
 */
public class SystemActor extends AbstractActor {



    private static final ActorIdentity IDENTITY =
            ActorIdentity.of(
                    ActorType.SYSTEM,
                    ActorCode.of(ActorType.SYSTEM.getCodePrefix())
            );
    public static final SystemActor INSTANCE = new SystemActor();



    private SystemActor() {
        super(IDENTITY);
    }

    @Override
    public boolean hasAuthority(String authority) {
        return false;
    }
}