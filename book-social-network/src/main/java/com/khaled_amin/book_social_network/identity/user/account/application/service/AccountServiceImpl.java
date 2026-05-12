package com.khaled_amin.book_social_network.identity.user.account.application.service;

import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.core.provider.ActorProvider;
import com.khaled_amin.book_social_network.identity.core.exception.IdentityException;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.Email;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.EncodedPassword;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.user.account.application.policy.AccountPolicyContextFactory;
import com.khaled_amin.book_social_network.identity.user.account.application.policy.AccountPolicyEngine;
import com.khaled_amin.book_social_network.core.servise.EntityRetrievalService;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.user.role.application.service.RoleService;
import com.khaled_amin.book_social_network.identity.user.account.application.exception.AccountApplicationException;
import com.khaled_amin.book_social_network.identity.user.account.application.validation.AccountApplicationValidator;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountUpdateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.AccountFactory;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.account.domain.repository.AccountRepository;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.AccountId;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
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

        Actor actor = actorProvider.getCurrent();

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
    public Account update(ActorCode accountCode, AccountUpdateCommand command) {

        Account target = getByAccountCode(accountCode);
        Actor actor = actorProvider.getCurrent();

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
    public Account activate(ActorCode accountCode) {
        Account target = getByAccountCode(accountCode);
        target.activate();
        return accountRepository.save(target);
    }

    @Transactional
    @Override
    public void resetPassword(ActorCode accountCode, EncodedPassword encodedPassword) {
        Account target = getByAccountCode(accountCode);
        target.resetPassword(encodedPassword);
        accountRepository.save(target);
    }

    @Transactional
    @Override
    public Account assignRole(ActorCode accountCode, RoleName roleName) {

        Account target = getByAccountCode(accountCode);
        Role role = roleService.getByName(roleName);
        Actor actor = actorProvider.getCurrent();

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
    public Account assignRoles(ActorCode accountCode, List<String> roleNames) {

        List<RoleName> normalizedRoleNames = normalizeRoleNames(roleNames);
        Account target = getByAccountCode(accountCode);
        Actor actor = actorProvider.getCurrent();
        List<Role> fetchedRoles = roleService.getAllByNames(normalizedRoleNames);

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
    public Account removeRole(ActorCode accountCode, RoleName roleName) {

        Account target = getByAccountCode(accountCode);
        Role role = roleService.getByName(roleName);
        Actor actor = actorProvider.getCurrent();


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
    public Account replaceRoles(ActorCode accountCode, List<String> roleNames) {


        List<RoleName> normalizedRoleNames = normalizeRoleNames(roleNames);
        Account target = getByAccountCode(accountCode);
        Actor actor = actorProvider.getCurrent();

        List<Role> fetchedRoles = roleService.getAllByNames(normalizedRoleNames);

        // Policy validation
        accountPolicyEngine.canRepaceRoles(
                policyContextFactory.forReplace(actor, target, target.getRoles(), fetchedRoles)
        );

        // Business rule
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

    @Override
    public Optional<Account> getOptionalByEmail(String email) {
        return accountRepository.findByEmail(email);
    }


    @Override
    public Account getByEmail(String email) {
        return getOptionalByEmail(email)
                .orElseThrow(AccountApplicationException::notFound);
    }

    @Override
    public Account getByEmail(Email email) {
        return getByEmail(email.value());
    }

    @Override
    public Account getByIdentity(ActorIdentity identity) {

        if (identity == null) {
            throw IdentityException.notFound()
                    .withDetail("reason", "Actor identity cannot be null");
        }

        ActorCode accountCode = identity.getActorCode();

        Account account = getOptionalByAccountCode(accountCode)
                .orElseThrow(() -> AccountApplicationException.notFound()
                        .withDetail("reason", "Account not found for given identity")
                        .withDetail("actorType", identity.getActorType().name())
                        .withDetail("actorCode", identity.getActorCode().toString())
                );

        if (!account.getActorIdentity().sameAs(identity)) {
            throw AccountApplicationException.notFound()
                    .withDetail("reason", "Account not found for given identity")
                    .withDetail("actorType", identity.getActorType().name())
                    .withDetail("actorCode", identity.getActorCode().toString());
        }

        return account;
    }

    public Optional<Account> getOptionalByAccountCode(ActorCode accountCode) {
        return accountRepository.findByAccountCode(accountCode.getValue());
    }

    public Account getByAccountCode(ActorCode accountCode){
        return getOptionalByAccountCode(accountCode).orElseThrow(() -> AccountApplicationException.notFound()
                .withDetail("reason", "Account not found for given code")
                .withDetail("actorCode", accountCode.getValue())
        );
    }

    @Override
    public Optional<Account> getOptionalByRoleName(String roleName) {
        return accountRepository.findByRoleName(roleName);
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

    private List<RoleName> normalizeRoleNames(List<String> roleNames) {

        return roleNames.stream()
                .filter(Objects::nonNull)
                .map(RoleName::of)
                .distinct()
                .toList();
    }

    // --------------------------- End Private Helper Methods ------------------------- //
}
