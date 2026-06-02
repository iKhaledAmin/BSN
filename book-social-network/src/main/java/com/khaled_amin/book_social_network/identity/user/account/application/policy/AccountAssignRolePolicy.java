package com.khaled_amin.book_social_network.identity.user.account.application.policy;

import com.khaled_amin.book_social_network.core.policy.AbstractPolicy;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.user.account.application.actor.AccountActor;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountPolicyException;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountTechnicalException;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountAssignRolePolicy extends AbstractPolicy<AccountPolicyContext> {

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
        throw AccountPolicyException.assignRoleForbidden()
                .withClientDetails("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Assign role";
    }


    @Override
    protected void handleSystem(AccountPolicyContext context) {
        allow();
    }


    @Override
    protected void handleAccount(AccountPolicyContext context) {

        AccountActor actor = (AccountActor) context.getActor();
        Account target = context.getTarget();
        Role role = context.getRequestedRole();

        String superAdmin = SystemRole.SUPER_ADMIN.getName().toString();

        System.out.println("the sent role " + superAdmin);

        // --------------------  Prevent privilege escalation --------------------
        // Only SUPER_ADMIN can assign SUPER_ADMIN role
        if (superAdmin.equals(role.getName()) && !actor.hasRole(superAdmin)) {
            System.out.println("hee 1");
            deny("Only super admin can assign this role");
        }

        // -------------------- System roles protection --------------------
        // Only SUPER_ADMIN can assign any system role
        if (role.getRoleType().isSystem() && !actor.hasRole(superAdmin)) {
            System.out.println("gg5");
            deny("Only super admin can assign system role");
        }


        // -------------------- Self-assignment restriction --------------------
        // Prevent users from elevating themselves
        if (actor.sameAs(target.getActorIdentity())&& !actor.hasRole(superAdmin)) {
            deny("You cannot assign roles to yourself");
        }

        allow();
    }


}