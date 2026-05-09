package com.khaled_amin.book_social_network.identity.user.role.application.policy;

import com.khaled_amin.book_social_network.core.policy.core.AbstractPolicy;
import com.khaled_amin.book_social_network.identity.user.role.application.exception.RolePolicyException;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import org.springframework.stereotype.Component;

@Component
public class RoleCreateSystemRolePolicy extends AbstractPolicy<RolePolicyContext> {


    @Override
    public void validateContext(RolePolicyContext context) {

    }

    @Override
    protected Actor extractActor(RolePolicyContext context) {
        return context.getActor();
    }

    @Override
    protected void deny(String reason) {
        throw RolePolicyException
                .createSystemRoleForbidden()
                .withDetail("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Create system role";
    }

    @Override
    protected void handleSystem(RolePolicyContext context) {
        allow(); // only system can resolve system roles
    }

}
