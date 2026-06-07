package com.khaled_amin.book_social_network.identity.user.account.application.policy;

import com.khaled_amin.book_social_network.core.policy.AbstractPolicy;
import com.khaled_amin.book_social_network.core.utils.diff.DiffResult;
import com.khaled_amin.book_social_network.core.utils.diff.DiffUtils;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountPolicyException;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountTechnicalException;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Component
public class AccountReplaceRolesPolicy extends AbstractPolicy<AccountPolicyContext> {

    private final AccountAssignRolePolicy accountAssignRolePolicy;
    private final AccountRemoveRolePolicy accountRemoveRolePolicy;

    @Override
    public void validateContext(AccountPolicyContext context) {

        if (context == null)
            throw AccountTechnicalException.invalidPolicyContext()
                    .withDebugDetails("reason", "Account policy context is null");

        if(context.getActor() == null)
            throw AccountTechnicalException.invalidPolicyContext()
                    .withDebugDetails("reason","Actor is null");

        if (context.getTarget() == null) {
            throw AccountTechnicalException.invalidPolicyContext()
                    .withDebugDetails("reason","Target account is null");
        }

        if (context.getCurrentRoles() == null){
            throw AccountTechnicalException.invalidPolicyContext()
                    .withDebugDetails("reason","Current roles is null");
        }

        if (context.getNewRoles() == null){
            throw AccountTechnicalException.invalidPolicyContext()
                    .withDebugDetails("reason","New roles is null");
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
                .withClientDetails("reason", reason);
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
    protected void handleAccount(AccountPolicyContext context) {

        Actor actor = context.getActor();
        Account target = context.getTarget();

        Set<Role> currentRoles = context.getCurrentRoles();
        List<Role> newRoles = context.getNewRoles();

        DiffResult<Role> diff = DiffUtils.diff(
                currentRoles,
                newRoles,
                Role::getId
        );

        // no changes → nothing to verifyToken
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