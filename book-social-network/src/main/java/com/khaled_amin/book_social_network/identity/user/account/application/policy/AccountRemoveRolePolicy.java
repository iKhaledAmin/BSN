package com.khaled_amin.book_social_network.identity.user.account.application.policy;

import com.khaled_amin.book_social_network.core.policy.AbstractPolicy;
import com.khaled_amin.book_social_network.identity.user.account.application.actor.AccountActor;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountPolicyException;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountTechnicalException;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import org.springframework.stereotype.Component;


@Component
public class AccountRemoveRolePolicy extends AbstractPolicy<AccountPolicyContext> {

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

        if (context.getRequestedRole() == null) {
            throw AccountTechnicalException.invalidPolicyContext()
                    .withDebugDetails("reason","Requested role is null");
        }
    }

    @Override
    protected Actor extractActor(AccountPolicyContext context) {
        return context.getActor();
    }

    @Override
    protected void deny(String reason) {
        throw AccountPolicyException.roleRemovalForbidden()
                .withClientDetails("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Remove role";
    }


    @Override
    protected void handleSystem(AccountPolicyContext context) {
        // SYSTEM is trusted (batch jobs, definition, internalServer processes)
        allow();
    }

    @Override
    protected void handleAccount(AccountPolicyContext context) {

        AccountActor actor = (AccountActor) context.getActor();
        Account target = context.getTarget();
        Role role = context.getRequestedRole();

        String superAdmin = SystemRole.SUPER_ADMIN.getName().toString();

        // Prevent self-role removal (except SUPER_ADMIN)
        // A account should not downgrade/remove their own privileges unless they are SUPER_ADMIN
        if (actor.sameAs(target.getActorIdentity()) && !actor.hasRole(superAdmin)) {

            deny("You cannot remove your own role");
        }

        // Only SUPER_ADMIN can remove SUPER_ADMIN role from any account
        if (superAdmin.equals(role.getName()) && !actor.hasRole(superAdmin)) {

            deny("Only super admin can remove this role");
        }

        // System roles are critical → only SUPER_ADMIN is allowed
        if (role.getRoleType().isSystem() && !actor.hasRole(superAdmin)) {

            deny("Only super admin can remove system roles");
        }

        allow();
    }

    // -------------------- Helpers -------------------- //


}