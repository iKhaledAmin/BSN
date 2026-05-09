package com.khaled_amin.book_social_network.identity.core.model;


/**
 * Represents a system-level actor executing internal operations.
 *
 * <p>
 * This actor is used for non-user initiated processes such as:
 * </p>
 * <ul>
 *   <li>Application startup routines</li>
 *   <li>Scheduled jobs</li>
 *   <li>Background processing</li>
 *   <li>Infrastructure-triggered workflows</li>
 * </ul>
 *
 * <h3>Characteristics</h3>
 * <ul>
 *   <li>Has a fixed {@link ActorType#SYSTEM} identity</li>
 *   <li>Does not rely on role-based authorization</li>
 * </ul>
 *
 * <h3>Security Semantics</h3>
 * <ul>
 *   <li>Policies may grant elevated privileges to system actors</li>
 *   <li>Must be used carefully to avoid bypassing business rules</li>
 * </ul>
 *
 * @see ActorType
 * @see AbstractActor
 */

public class SystemActor extends AbstractActor {

    public SystemActor() {
        super(ActorIdentity.of(ActorType.SYSTEM, "SYSTEM"));
    }

    @Override
    public boolean hasAuthority(String authority) {
        return false;
    }

}