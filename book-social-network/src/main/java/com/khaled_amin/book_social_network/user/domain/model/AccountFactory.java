package com.khaled_amin.book_social_network.user.domain.model;

import com.khaled_amin.book_social_network.role.domain.model.Role;
import com.khaled_amin.book_social_network.user.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.user.domain.command.ProfileCreateCommand;
import com.khaled_amin.book_social_network.user.domain.value.EncodedPassword;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class AccountFactory {

    private final PasswordEncoder passwordEncoder;

    public Account create(AccountCreateCommand command, List<Role> roles) {

        Profile profile = createProfile(command.profileCommand());

        EncodedPassword encodedPassword = EncodedPassword.of(
                encodePassword(
                        command.password().value()
                )
        );

        return Account.create(
                command.username(),
                encodedPassword,
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

    private String encodePassword(String raw) {
        return passwordEncoder.encode(raw);
    }
}


