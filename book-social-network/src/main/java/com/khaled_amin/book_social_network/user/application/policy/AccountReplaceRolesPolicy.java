package com.khaled_amin.book_social_network.user.application.policy;

import com.khaled_amin.book_social_network.core.policy.AbstractPolicy;
import com.khaled_amin.book_social_network.core.utils.diff.DiffResult;
import com.khaled_amin.book_social_network.core.utils.diff.DiffUtils;
import com.khaled_amin.book_social_network.role.domain.model.Role;
import com.khaled_amin.book_social_network.core.actor.Actor;
import com.khaled_amin.book_social_network.user.application.exception.AccountPolicyException;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class AccountReplaceRolesPolicy extends AbstractPolicy<AccountPolicyContext> {

    private final AccountAssignRolePolicy accountAssignRolePolicy;
    private final AccountRemoveRolePolicy accountRemoveRolePolicy;

    @Override
    public void validateContext(AccountPolicyContext context) {

        if (context.getTarget() == null) {
            throw AccountPolicyException.invalidPolicyContext("Target must not be null");
        }

        if (context.getCurrentRoles() == null) {
            throw AccountPolicyException.invalidPolicyContext("Current roles must not be null");
        }

        if (context.getNewRoles() == null) {
            throw AccountPolicyException.invalidPolicyContext("New roles must not be null");
        }
    }

    @Override
    protected Actor extractActor(AccountPolicyContext context) {
        return context.getActor();
    }

    @Override
    protected void deny(String reason) {
        throw AccountPolicyException
                .roleReplacementForbidden()
                .withDetail("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Replace roles";
    }

    @Override
    protected void handleSystem(AccountPolicyContext context) {
        allow(); // system can replace roles freely
    }

    @Override
    protected void handleUser(AccountPolicyContext context) {

        Actor actor = context.getActor();
        Account target = context.getTarget();

        List<Role> currentRoles = context.getCurrentRoles();
        List<Role> newRoles = context.getNewRoles();

        DiffResult<Role> diff = DiffUtils.diff(
                currentRoles,
                newRoles,
                Role::getId
        );

        // no changes → nothing to validate
        if (!diff.hasChanges()) {
            allow();
        }

        List<Role> toAdd = diff.getToAdd();
        List<Role> toRemove = diff.getToRemove();

        // Validate role additions
        for (Role role : toAdd) {

            AccountPolicyContext roleContext = AccountPolicyContext.builder()
                    .actor(actor)
                    .target(target)
                    .requestedRole(role)
                    .build();

            accountAssignRolePolicy.check(roleContext);
        }

        // Validate role removals
        for (Role role : toRemove) {

            AccountPolicyContext roleContext = AccountPolicyContext.builder()
                    .actor(actor)
                    .target(target)
                    .requestedRole(role)
                    .build();

            accountRemoveRolePolicy.check(roleContext);
        }

        allow();
    }
}