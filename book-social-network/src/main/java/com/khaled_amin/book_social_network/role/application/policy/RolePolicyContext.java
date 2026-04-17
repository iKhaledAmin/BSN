package com.khaled_amin.book_social_network.role.application.policy;

import com.khaled_amin.book_social_network.core.policy.BasePolicyContext;
import com.khaled_amin.book_social_network.role.domain.command.CreateRoleCommand;
import com.khaled_amin.book_social_network.role.domain.command.UpdateRoleCommand;
import com.khaled_amin.book_social_network.role.domain.model.Role;
import com.khaled_amin.book_social_network.core.actor.Actor;
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