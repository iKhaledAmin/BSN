package com.khaled_amin.book_social_network.identity.user.account.application.policy;

import com.khaled_amin.book_social_network.core.policy.BasePolicyContext;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

@Getter
@Builder(access = AccessLevel.PACKAGE)
public class AccountPolicyContext implements BasePolicyContext {

    private final Actor actor;   // who performs action
    private final Account target;  // affected account

    private final Role requestedRole; // role requested by the account
    private final List<Role> requestedRoles; // roles requested by the account

    private final Set<Role> currentRoles; // existing roles
    private final List<Role> newRoles;     // desired roles
}
