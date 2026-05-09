package com.khaled_amin.book_social_network.identity.user.role.application.policy;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RolePolicyEngine {

    private final RoleCreateBusinessRolePolicy roleCreateBusinessRolePolicy;
    private final RoleCreateSystemRolePolicy roleCreateSystemRolePolicy;
    private final RoleUpdatePolicy roleUpdatePolicy;
    private final RoleDeletePolicy roleDeletePolicy;


    public void canCreateBusinessRole(RolePolicyContext context) {
        roleCreateBusinessRolePolicy.check(context);
    }

    public void canCreateSystemRole(RolePolicyContext context) {
        roleCreateSystemRolePolicy.check(context);
    }

    public void canUpdateRole(RolePolicyContext context) {
        roleUpdatePolicy.check(context);
    }

    public void canDeleteRole(RolePolicyContext context) {
        roleDeletePolicy.check(context);
    }
}