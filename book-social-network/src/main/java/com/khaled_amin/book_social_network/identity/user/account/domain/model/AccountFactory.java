package com.khaled_amin.book_social_network.identity.user.account.domain.model;

import com.khaled_amin.book_social_network.identity.core.generator.ActorCodeGenerator;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.ProfileCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class AccountFactory {

    private final ActorCodeGenerator actorCodeGenerator;


    public Account create(
            String username,
            String encodedPassword,
            String email,
            String firstName,
            String lastName,
            List<Role> roles
    ){

        ActorCode accountCode = actorCodeGenerator.generate(ActorType.ACCOUNT);

        AccountCreateCommand command = AccountCreateCommand.of(accountCode, username, encodedPassword, email);

        Profile profile = createProfile(firstName, lastName);

        return Account.create(command, profile, roles);
    }



    private Profile createProfile(String firstName,String lastName) {

        ProfileCreateCommand command = ProfileCreateCommand.of(firstName, lastName);

        return Profile.create(command);
    }

}


