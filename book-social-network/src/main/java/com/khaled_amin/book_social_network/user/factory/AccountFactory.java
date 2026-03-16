package com.khaled_amin.book_social_network.user.factory;

import com.khaled_amin.book_social_network.role.model.entity.Role;
import com.khaled_amin.book_social_network.user.model.dto.RegistrationRequest;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import com.khaled_amin.book_social_network.user.model.entity.Profile;
import com.khaled_amin.book_social_network.user.model.enums.AccountStatus;
import com.khaled_amin.book_social_network.user.model.enums.ProfileStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AccountFactory {

    private final PasswordEncoder passwordEncoder;

    public Account createAccount(RegistrationRequest request, List<Role> roles) {

        Profile profile = Profile.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .profileStatus(ProfileStatus.getDefault())
                .build();

        Account account = Account.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .emailAddress(request.getEmailAddress())
                .accountStatus(AccountStatus.getDefault())
                .build();

        account.attachProfile(profile);
        account.assignRole(roles);

        return account;
    }

}