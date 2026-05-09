package com.khaled_amin.book_social_network.identity.user.account.application.policy;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccountPolicyEngine {

    private final AccountAssignRolePolicy accountAssignRolePolicy;
    private final AccountRemoveRolePolicy accountRemoveRolePolicy;
    private final AccountReplaceRolesPolicy accountReplaceRolesPolicy;
    private final AccountCreatePolicy accountCreatePolicy;
    private final AccountUpdatePolicy accountUpdatePolicy;


    public void canAssignRole(AccountPolicyContext context) {
        accountAssignRolePolicy.check(context);
    }

    public void canRemoveRole(AccountPolicyContext context) {
        accountRemoveRolePolicy.check(context);
    }

    public void canRepaceRoles(AccountPolicyContext context) {
        accountReplaceRolesPolicy.check(context);
    }

    public void canCreate(AccountPolicyContext context) {
        accountCreatePolicy.check(context);
    }

    public void canUpdate(AccountPolicyContext context) {
        accountUpdatePolicy.check(context);
    }
}