package com.khaled_amin.book_social_network.user.application.service;

import com.khaled_amin.book_social_network.core.actor.ActorProvider;
import com.khaled_amin.book_social_network.role.domain.value.RoleId;
import com.khaled_amin.book_social_network.core.actor.Actor;
import com.khaled_amin.book_social_network.user.application.policy.AccountPolicyContextFactory;
import com.khaled_amin.book_social_network.user.application.policy.AccountPolicyEngine;
import com.khaled_amin.book_social_network.core.servise.EntityRetrievalService;
import com.khaled_amin.book_social_network.role.domain.model.Role;
import com.khaled_amin.book_social_network.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.role.application.service.RoleService;
import com.khaled_amin.book_social_network.user.application.exception.AccountApplicationException;
import com.khaled_amin.book_social_network.user.application.validation.AccountApplicationValidator;
import com.khaled_amin.book_social_network.user.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.user.domain.command.AccountUpdateCommand;
import com.khaled_amin.book_social_network.user.domain.model.AccountFactory;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import com.khaled_amin.book_social_network.user.domain.repository.AccountRepository;
import com.khaled_amin.book_social_network.user.domain.value.AccountId;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;
    private final RoleService roleService;
    private final ActorProvider actorProvider;
    private final EntityRetrievalService entityRetrievalService;
    private final AccountPolicyEngine accountPolicyEngine;
    private final AccountApplicationValidator accountValidator;
    private final AccountPolicyContextFactory policyContextFactory;


    @Transactional
    @Override
    public Account create(AccountCreateCommand command, List<Role> roles) {

        Actor actor = actorProvider.getCurrentActor();

        // Application validation
        accountValidator.validateCreate(command,roles);

        // Policy validation
        accountPolicyEngine.canCreate(
                policyContextFactory.forCreate(actor, roles)
        );

        // Domain logic
        Account account = accountFactory.create(command, roles);

        // Persist
        return accountRepository.save(account);
    }


    @Transactional
    @Override
    public Account update(AccountId accountId, AccountUpdateCommand command) {

        Account target = getById(accountId.value());
        Actor actor = actorProvider.getCurrentActor();

        // Application validation
        accountValidator.validateUpdate(target, command);

        // Policy validation
        accountPolicyEngine.canUpdate(
                policyContextFactory.forUpdate(actor, target)
        );

        // Domain logic
        target.update(command);

        // Persist
        return accountRepository.save(target);
    }

    @Transactional
    @Override
    public Account assignRoles(AccountId accountId, RoleId roleId) {

        Account target = getById(accountId.value());
        Role role = roleService.getById(roleId.value());
        Actor actor = actorProvider.getCurrentActor();

        // Policy Validation
        accountPolicyEngine.canAssignRole(
                policyContextFactory.forAssign(actor, target, role)
        );

        // Domain logic
        target.assignRole(role);

        // Persist
        return accountRepository.save(target);
    }

    @Transactional
    @Override
    public Account assignRoles(AccountId accountId, List<Long> roleIds) {

        List<Long> normalizedIds = normalizeRoleIds(roleIds);
        Account target = getById(accountId.value());
        Actor actor = actorProvider.getCurrentActor();
        List<Role> fetchedRoles = roleService.getAllByIds(normalizedIds);

        // Application validation
        accountValidator.validateAccountRoles(normalizedIds, fetchedRoles);

        // Policy validation
        for (Role role : fetchedRoles) {
            accountPolicyEngine.canAssignRole(
                    policyContextFactory.forAssign(actor, target, role)
            );
        }

        // Domain logic
        target.assignRoles(fetchedRoles);

        return accountRepository.save(target);
    }

    @Transactional
    @Override
    public Account removeRole(AccountId accountId,RoleId roleId) {

        Account target = getById(accountId.value());
        Role role = roleService.getById(roleId.value());
        Actor actor = actorProvider.getCurrentActor();


        // Policy Validation
        accountPolicyEngine.canRemoveRole(
                policyContextFactory.forRemove(actor, target, role)
        );

        // Application-business-rule
        ensureAtLeastSuperAdminStillExists(target,role);

        // Domain logic
        target.removeRole(role);

        // Persist
        return accountRepository.save(target);
    }

    @Transactional
    @Override
    public Account replaceRoles(AccountId accountId, List<Long> roleIds) {

        List<Long> normalizedIds = normalizeRoleIds(roleIds);
        Account target = getById(accountId.value());
        Actor actor = actorProvider.getCurrentActor();
        List<Role> fetchedRoles = roleService.getAllByIds(normalizedIds);

        // Application validation
        accountValidator.validateAccountRoles(normalizedIds,fetchedRoles);

        // Policy Validation
        accountPolicyEngine.canRepaceRoles(
                policyContextFactory.forReplace(actor, target, target.getRoles(), fetchedRoles)
        );

        // Application-business-rule
        ensureAtLeastSuperAdminStillExists(target, fetchedRoles);

        // Domain logic
        target.replaceRoles(fetchedRoles);

        // Persist
        return accountRepository.save(target);
    }


    // -------------------------------- Retrieval -------------------------------- //

    @Override
    public Optional<Account> getOptionalById(Long id) {
        return entityRetrievalService.getOptionalById(Account.class, id);
    }

    @Override
    public Account getById(Long id) {
        return entityRetrievalService.getById(
                Account.class,
                id,
                AccountApplicationException::notFound
        );
    }

    @Override
    public Account getById(AccountId accountId) {
        return getById(accountId.value());
    }

    @Override
    public Optional<Account> getOptionalByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    @Override
    public Account getByUsername(String username) {
        return getOptionalByUsername(username)
                .orElseThrow(AccountApplicationException::notFound);
    }

    @Override
    public boolean existsByRoleName(String roleName) {
        return accountRepository.existsByRoleName(roleName);
    }

    // ------------------------------- End Retrieval ------------------------------ //




    // ------------------------------Application Business Rule---------------------------- //



    private void ensureAtLeastSuperAdminStillExists(Account target, Role role){
        ensureAtLeastSuperAdminStillExists(target,List.of(role));
    }

    private void ensureAtLeastSuperAdminStillExists(Account target, List<Role> newRoles) {

        boolean targetWasSuperAdmin = target.hasRole(SystemRole.SUPER_ADMIN.getName().value());

        boolean willStillBeSuperAdmin = newRoles
                .stream()
                .anyMatch(r -> SystemRole.SUPER_ADMIN.getName().value().equals(r.getName()));

        if (targetWasSuperAdmin && !willStillBeSuperAdmin) {

            long currentSuperAdminCount = accountRepository.countByRoleName(
                    SystemRole.SUPER_ADMIN.getName().value()
            );

            if (currentSuperAdminCount <= 1) {
                throw AccountApplicationException
                        .lastSuperAdmin();
            }
        }
    }

    // --------------------------- End Application Business Rule ------------------------- //


    // --------------------------- Private Helper Methods ------------------------- //


    private List<Long> normalizeRoleIds(List<Long> roleIds) {
        return roleIds.stream()
                .filter(Objects::nonNull)
                .distinct()
                .toList();
    }

    // --------------------------- End Private Helper Methods ------------------------- //
}
