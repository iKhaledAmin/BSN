package com.khaled_amin.book_social_network.identity.user.role.application.policy;

import com.khaled_amin.book_social_network.core.policy.core.BasePolicyContext;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.CreateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.UpdateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder(access = AccessLevel.PACKAGE)
public class RolePolicyContext implements BasePolicyContext {

    private final Actor actor;

    private final Role role;

    private final CreateRoleCommand createCommand;
    private final UpdateRoleCommand updateCommand;
}