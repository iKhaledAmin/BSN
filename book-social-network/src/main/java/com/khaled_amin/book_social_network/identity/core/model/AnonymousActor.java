package com.khaled_amin.book_social_network.identity.core.model;

/**
 * Represents an unauthenticated actor interacting with the system.
 *
 * <p>
 * Used for requests without authenticated identity.
 * </p>
 */
public class AnonymousActor extends AbstractActor {

    private static final ActorIdentity IDENTITY =
            ActorIdentity.of(
                    ActorType.ANONYMOUS,
                    ActorCode.of(ActorType.ANONYMOUS.getCodePrefix())
            );
    public static final AnonymousActor INSTANCE = new AnonymousActor();



    private AnonymousActor() {
        super(IDENTITY);
    }

    @Override
    public boolean hasAuthority(String authority) {
        return false;
    }
}