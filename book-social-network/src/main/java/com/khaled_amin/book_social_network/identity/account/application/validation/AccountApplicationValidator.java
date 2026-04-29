package com.khaled_amin.book_social_network.user.application.validation;

import com.khaled_amin.book_social_network.role.domain.model.Role;
import com.khaled_amin.book_social_network.user.application.exception.AccountApplicationException;
import com.khaled_amin.book_social_network.user.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.user.domain.command.AccountUpdateCommand;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import com.khaled_amin.book_social_network.user.domain.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class AccountApplicationValidator {

    private final AccountRepository accountRepository;

    // ---------------- CREATE ---------------- //


    public void validateCreate(AccountCreateCommand command, List<Role> roles) {
        if (command == null){
            throw AccountApplicationException
                    .invalidCommand()
                    .withDetail("reason" , "Create command cannot be null");
        }

        if (roles == null || roles.isEmpty()) {
            throw AccountApplicationException
                    .invalidAccountRoles()
                    .withDetail("reason", "Account roles cannot be null or empty");
        }

        ensureUsernameUnique(command.username().value());
        ensureEmailUnique(command.email().value());
    }
    // ---------------- UPDATE ---------------- //

    public void validateUpdate(Account account, AccountUpdateCommand command) {
        if (command == null){
            throw AccountApplicationException
                    .invalidCommand()
                    .withDetail("reason" , "Update command cannot be null");
        }


        // email uniqueness (PATCH-safe)
        command.email().ifPresent(email -> {
            if (!account.getEmailAddress().equals(email.value())) {
                ensureEmailUnique(email.value());
            }
        });
    }

    // ---------------- DELETE ---------------- //


    // ---------------- VALIDATE ACCOUNT ROLES ---------------- //

    public void validateAccountRoles(List<Long> roleIds, List<Role> fetchedRoles) {

        if (roleIds == null || roleIds.isEmpty()) {
            throw AccountApplicationException
                    .invalidAccountRoleIds()
                    .withDetail("reason", "Account role ids must not be null or empty");
        }

        if (fetchedRoles.size() != roleIds.size()) {

            Set<Long> foundIds = fetchedRoles.stream()
                    .map(Role::getId)
                    .collect(Collectors.toSet());

            List<Long> notFound = roleIds.stream()
                    .filter(id -> !foundIds.contains(id))
                    .toList();

            throw AccountApplicationException
                    .AccountRolesNotFound()
                    .withDetail("notFoundRoleIds", notFound);
        }
    }

    // ---------------- PRIVATE ---------------- //

    private void ensureUsernameUnique(String username) {
        if (accountRepository.existsByUsername(username)) {
            throw AccountApplicationException
                    .usernameAlreadyExists()
                    .withDetail("username", username);
        }
    }

    private void ensureEmailUnique(String email) {
        if (accountRepository.existsByEmail(email)) {
            throw AccountApplicationException
                    .emailAlreadyExists()
                    .withDetail("email", email);
        }
    }


}