package com.khaled_amin.book_social_network.user.service.impl;

import com.khaled_amin.book_social_network.authorization.policy.PolicyEngine;
import com.khaled_amin.book_social_network.authorization.policy.role.RolePolicyContext;
import com.khaled_amin.book_social_network.common.servise.EntityRetrievalService;
import com.khaled_amin.book_social_network.common.utils.diff.DiffResult;
import com.khaled_amin.book_social_network.common.utils.diff.DiffUtils;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import com.khaled_amin.book_social_network.role.model.enums.SystemRoles;
import com.khaled_amin.book_social_network.role.service.RoleService;
import com.khaled_amin.book_social_network.security.CurrentUserService;
import com.khaled_amin.book_social_network.user.exception.AccountException;
import com.khaled_amin.book_social_network.user.factory.AccountFactory;
import com.khaled_amin.book_social_network.user.model.dto.AccountUpdateRequest;
import com.khaled_amin.book_social_network.user.model.dto.RegistrationRequest;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import com.khaled_amin.book_social_network.user.model.mapper.AccountNormalMapper;
import com.khaled_amin.book_social_network.user.repository.AccountRepo;
import com.khaled_amin.book_social_network.user.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@AllArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {

    private final AccountRepo accountRepo;
    private final AccountFactory accountFactory;
    private final AccountNormalMapper accountNormalMapper;
    private final RoleService roleService;
    private final CurrentUserService currentUserService;
    private final EntityRetrievalService entityRetrievalService;
    private final PolicyEngine policyEngine;




    @Transactional
    @Override
    public Account register(RegistrationRequest request) {

        validateRegistration(request);

        List<Role> defaultRoles = roleService.getDefaultRoles();

        Account account = accountFactory.createAccount(request, defaultRoles);

        return accountRepo.save(account);
    }


    @Transactional
    @Override
    public Account update(Long accountId, AccountUpdateRequest request) {

        Account account = getById(accountId);

        validateAccountUpdate(account, request);

        accountNormalMapper.updateEntity(request, account);

        return accountRepo.save(account);
    }


    @Transactional
    @Override
    public Account assignRole(Long accountId, Long roleId) {

        Account target = getById(accountId);
        Role role = roleService.getById(roleId);
        Account actor = currentUserService.getCurrentAccount();

        if (target.hasRole(role.getId())) {
            throw AccountException.roleAlreadyAssigned();
        }

        RolePolicyContext context = RolePolicyContext.builder()
                .actor(actor)
                .target(target)
                .role(role)
                .build();

        policyEngine.canAssign(context);

        target.assignRole(role);

        return accountRepo.save(target);
    }

    @Transactional
    @Override
    public Account removeRole(Long accountId, Long roleId) {

        Account target = getById(accountId);
        Role role = roleService.getById(roleId);
        Account actor = currentUserService.getCurrentAccount();

        if (!target.hasRole(role.getId())) {
            throw AccountException.roleNotAssigned();
        }

        long superAdminCount = accountRepo.countByAccountRolesRoleSystemCode(
                SystemRoles.SUPER_ADMIN.getSystemCode()
        );

        RolePolicyContext context = RolePolicyContext.builder()
                .actor(actor)
                .target(target)
                .role(role)
                .superAdminCount(superAdminCount)
                .build();

        policyEngine.canRemove(context);

        target.removeRole(role);

        return accountRepo.save(target);
    }

    @Transactional
    @Override
    public Account setRoles(Long accountId, List<Long> roleIds) {

        Account target = getById(accountId);
        Account actor = currentUserService.getCurrentAccount();

        // Remove duplicates
        Set<Long> uniqueRoleIds = new HashSet<>(roleIds);

        // Fetch roles in ONE query
        List<Role> newRoles = roleService.getAllByIds(new ArrayList<>(uniqueRoleIds));

        // Compute diff
        DiffResult<Role> diff = DiffUtils.diff(
                target.getRoles(),
                newRoles,
                Role::getId
        );

        if (!diff.hasChanges()) {
            return target;
        }

        // Preload admin count
        long superAdminCount = accountRepo.countByAccountRolesRoleSystemCode(
                SystemRoles.SUPER_ADMIN.getSystemCode()
        );

        // Apply ADD
        for (Role role : diff.getToAdd()) {
            policyEngine.canAssign(
                    RolePolicyContext.builder()
                            .actor(actor)
                            .target(target)
                            .role(role)
                            .build()
            );

            target.assignRole(role);
        }

        // 7. Apply REMOVE
        for (Role role : diff.getToRemove()) {
            policyEngine.canRemove(
                    RolePolicyContext.builder()
                            .actor(actor)
                            .target(target)
                            .role(role)
                            .superAdminCount(superAdminCount)
                            .build()
            );

            target.removeRole(role);
        }

        return accountRepo.save(target);
    }


    // -------------------------------- RETRIEVAL -------------------------------- //

    @Override
    public Optional<Account> getOptionalById(Long id) {
        return entityRetrievalService.getOptionalById(Account.class, id);
    }

    @Override
    public Account getById(Long id) {
        return entityRetrievalService.getById(
                Account.class,
                id,
                AccountException::notFound
        );
    }

    @Override
    public Optional<Account> getOptionalByUsername(String username) {
        return accountRepo.findByUsername(username);
    }

    @Override
    public Account getByUsername(String username) {
        return getOptionalByUsername(username)
                .orElseThrow(AccountException::notFound);
    }




    // -------------------------------- VALIDATION -------------------------------- //

    private void validateRegistration(RegistrationRequest request) {

        ensureUsernameAvailable(request.getUsername());
        ensureEmailAvailable(request.getEmailAddress());

    }

    private void validateAccountUpdate(Account account, AccountUpdateRequest request) {

        if (!account.getUsername().equals(request.getUsername())) {
            ensureUsernameAvailable(request.getUsername());
        }

        if (!account.getEmailAddress().equals(request.getEmailAddress())) {
            ensureEmailAvailable(request.getEmailAddress());
        }
    }

    private void ensureUsernameAvailable(String username) {

        if (accountRepo.existsByUsername(username)) {
            throw AccountException.usernameAlreadyExists(username);
        }

    }

    private void ensureEmailAvailable(String email) {

        if (accountRepo.existsByEmailAddress(email)) {
            throw AccountException.emailAlreadyExists(email);
        }

    }

    // -------------------------------- BUILDERS -------------------------------- //



    // -------------------------------- ROLE MANAGEMENT -------------------------------- //

}
