package com.khaled_amin.book_social_network.identity.user.account.application.service;

import com.khaled_amin.book_social_network.core.pagination.PageResult;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.core.provider.ActorProvider;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountCreateRequest;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountPageRequest;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountUpdateRequest;
import com.khaled_amin.book_social_network.identity.user.account.api.mapper.AccountMapper;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.EncodedPassword;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.user.account.application.policy.AccountPolicyContextFactory;
import com.khaled_amin.book_social_network.identity.user.account.application.policy.AccountPolicyEngine;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.RawPassword;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountBusinessException;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountTechnicalException;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.user.role.application.service.RoleService;
import com.khaled_amin.book_social_network.identity.user.account.application.validation.AccountApplicationValidator;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountUpdateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.AccountFactory;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.account.domain.repository.AccountRepository;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepository accountRepository;
    private final AccountFactory accountFactory;
    private final AccountMapper accountMapper;
    private final RoleService roleService;
    private final ActorProvider actorProvider;
    private final AccountPolicyEngine accountPolicyEngine;
    private final AccountApplicationValidator accountValidator;
    private final AccountPolicyContextFactory policyContextFactory;
    private final PasswordEncoder passwordEncoder;


    @Transactional
    @Override
    public Account create(AccountCreateRequest request) {

        Actor actor = actorProvider.getCurrent();
        List<RoleName> roleNames = RoleName.of(request.getRoleNames());
        List<Role> roles = roleService.getAllByNames(roleNames);
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Application validation
        accountValidator.validateCreate(request);

        // Policy validation
        accountPolicyEngine.canCreate(
                policyContextFactory.forCreate(actor, roles)
        );

        // Domain logic
        Account account = accountFactory.create(
                request.getUsername(),
                encodedPassword,
                request.getEmailAddress(),
                request.getFirstName(),
                request.getLastName(),
                roles
        );

        // Persist
        return accountRepository.save(account);
    }


    @Transactional
    @Override
    public Account update(ActorCode accountCode, AccountUpdateRequest request) {

        Account target = getByAccountCode(accountCode);
        AccountUpdateCommand command = accountMapper.toCommand(request);
        Actor actor = actorProvider.getCurrent();

        // Application validation
        accountValidator.validateUpdate(target, request);

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
    public void resetPassword(ActorCode accountCode,RawPassword rawPassword) {

        Account target = getByAccountCode(accountCode);

        EncodedPassword encodedPassword = EncodedPassword.of(
                passwordEncoder.encode(rawPassword.toString())
        );

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
    public Account assignRoles(ActorCode accountCode, List<RoleName> roleNames) {

        // todo validate roleNames not null or empty


        Account target = getByAccountCode(accountCode);
        Actor actor = actorProvider.getCurrent();
        List<Role> fetchedRoles = roleService.getAllByNames(roleNames);

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
    public Account replaceRoles(ActorCode accountCode, List<RoleName> roleNames) {

        Account target = getByAccountCode(accountCode);
        Actor actor = actorProvider.getCurrent();

        List<Role> fetchedRoles = roleService.getAllByNames(roleNames);

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

    @Override
    public void login(ActorCode accountCode) {
        Account account = getByAccountCode(accountCode);
        account.login();
        accountRepository.save(account);
    }

    // -------------------------------- Retrieval -------------------------------- //


    @Override
    public boolean existsByRoleName(RoleName roleName) {
        return accountRepository.existsByRoleName(roleName);
    }

    @Override
    public Optional<Account> getOptionalByEmail(String email) {
        return accountRepository.findByEmail(email);
    }

    @Override
    public Optional<Account> getOptionalByUsername(String username) {
        return accountRepository.findByUsername(username);
    }

    public Optional<Account> getOptionalByAccountCode(ActorCode accountCode) {
        return accountRepository.findByAccountCode(accountCode.getValue());
    }

    public Account getByAccountCode(ActorCode accountCode){
        return getOptionalByAccountCode(accountCode).orElseThrow(() -> AccountBusinessException.notFound()
                .withClientDetails("reason", "Account not found for given code")
                .withClientDetails("actorCode", accountCode.getValue())
        );
    }

    @Override
    public Account getByIdentity(ActorIdentity identity) {

        if (identity == null) {
            throw AccountTechnicalException.nullActorIdentity();
        }

        ActorCode accountCode = identity.getActorCode();

        Account account = getOptionalByAccountCode(accountCode)
                .orElseThrow(() -> AccountBusinessException.notFound()
                        .withClientDetails("reason", "Account not found for given identity")
                        .withClientDetails("actorType", identity.getActorType().name())
                        .withClientDetails("actorCode", identity.getActorCode().toString())
                );

        if (!account.getActorIdentity().sameAs(identity)) {
            throw AccountBusinessException.notFound()
                    .withClientDetails("reason", "Account not found for given identity")
                    .withClientDetails("actorType", identity.getActorType().name())
                    .withClientDetails("actorCode", identity.getActorCode().toString());
        }

        return account;
    }


    public PageResult<Account> getAll(AccountPageRequest request) {
        return accountRepository.findAll(request);
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
                throw AccountBusinessException.lastSuperAdminRemovalNotAllowed();
            }
        }
    }

    // --------------------------- End Application Business Rule ------------------------- //


}
