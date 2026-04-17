package com.khaled_amin.book_social_network.core.actor;


public interface Actor {

    ActorIdentity getIdentity();
    ActorType getType();

    boolean hasRole(String roleName);
    boolean hasAnyRole(String... roleNames);


    // future
//    boolean hasPermission(String permission);
//    boolean hasAnyPermission(String... permissions);


    boolean sameAs(ActorIdentity other);

}
