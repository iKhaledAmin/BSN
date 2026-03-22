package com.khaled_amin.book_social_network.authorization.policy.role;

import com.khaled_amin.book_social_network.authorization.policy.base.PolicyContext;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RolePolicyContext implements PolicyContext {

    private final Account actor;   // who performs action
    private final Account target;  // affected account
    private final Role role;       // role being changed
    private long superAdminCount;
}