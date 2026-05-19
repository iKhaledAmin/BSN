package com.khaled_amin.book_social_network.identity.user.account.application.policy;

import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class AccountPolicyContextFactory {


    // -------------------- Create -------------------- //

    public AccountPolicyContext forCreate(Actor actor, List<Role> roles) {
        return AccountPolicyContext.builder()
                .actor(actor)
                .requestedRoles(roles)
                .build();
    }

    // -------------------- Update -------------------- //

    public AccountPolicyContext forUpdate(Actor actor, Account target) {
        return AccountPolicyContext.builder()
                .actor(actor)
                .target(target)
                .build();
    }

    // -------------------- Assign Role -------------------- //

    public AccountPolicyContext forAssign(Actor actor, Account target, Role role) {
        return AccountPolicyContext.builder()
                .actor(actor)
                .target(target)
                .requestedRole(role)
                .build();
    }

    // -------------------- Remove Role -------------------- //

    public AccountPolicyContext forRemove(Actor actor, Account target, Role role) {
        return AccountPolicyContext.builder()
                .actor(actor)
                .target(target)
                .requestedRole(role)
                .build();
    }

    // -------------------- Replace Roles -------------------- //

    public AccountPolicyContext forReplace(
            Actor actor,
            Account target,
            Set<Role> currentRoles,
            List<Role> newRoles
    ) {
        return AccountPolicyContext.builder()
                .actor(actor)
                .target(target)
                .currentRoles(currentRoles)
                .newRoles(newRoles)
                .build();
    }


}