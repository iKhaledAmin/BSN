package com.khaled_amin.book_social_network.identity.user.role.application.policy;

import com.khaled_amin.book_social_network.identity.user.role.domain.command.CreateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.UpdateRoleCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import org.springframework.stereotype.Component;

@Component
public class RolePolicyContextFactory {

    public RolePolicyContext forCreateBusinessRole(Actor actor, CreateRoleCommand createCommand) {
        return RolePolicyContext.builder()
                .actor(actor)
                .createCommand(createCommand)
                .build();
    }

    public RolePolicyContext forCreateSystemRole(Actor actor){
        return RolePolicyContext.builder()
                .actor(actor)
                .build();
    }

    public RolePolicyContext forUpdate(Actor actor, Role exsitingRole, UpdateRoleCommand updateCommand) {
        return RolePolicyContext.builder()
                .actor(actor)
                .role(exsitingRole)
                .updateCommand(updateCommand)
                .build();
    }

    public RolePolicyContext forDelete(Actor actor, Role role) {
        return RolePolicyContext.builder()
                .actor(actor)
                .role(role)
                .build();
    }
}