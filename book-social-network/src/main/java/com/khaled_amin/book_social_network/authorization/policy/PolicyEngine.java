package com.khaled_amin.book_social_network.authorization.policy;


import com.khaled_amin.book_social_network.authorization.policy.role.RoleAssignmentPolicy;
import com.khaled_amin.book_social_network.authorization.policy.role.RolePolicyContext;
import com.khaled_amin.book_social_network.authorization.policy.role.RoleRemovalPolicy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PolicyEngine {

    private final RoleAssignmentPolicy roleAssignmentPolicy;
    private final RoleRemovalPolicy roleRemovalPolicy;


    public void canAssign(RolePolicyContext context) {
        roleAssignmentPolicy.check(context);
    }

    public void canRemove(RolePolicyContext context) {
        roleRemovalPolicy.check(context);
    }
}