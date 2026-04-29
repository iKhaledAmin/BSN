package com.khaled_amin.book_social_network.user.domain.model;

import com.khaled_amin.book_social_network.role.domain.model.Role;
import com.khaled_amin.book_social_network.user.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.user.domain.command.ProfileCreateCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class AccountFactory {


    public Account create(AccountCreateCommand command, List<Role> roles) {

        Profile profile = createProfile(command.profileCommand());

        return Account.create(
                command.username(),
                command.encodedPassword(),
                command.email(),
                profile,
                roles
        );

    }




    private Profile createProfile(ProfileCreateCommand command) {
        return Profile.create(
                command.firstName(),
                command.lastName()
        );
    }

}


