package com.khaled_amin.book_social_network.identity.user.account.application.service;

import com.khaled_amin.book_social_network.identity.user.role.application.service.RoleUsageService;
import com.khaled_amin.book_social_network.identity.user.account.domain.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class RoleUsageServiceImpl implements RoleUsageService {

    private final AccountRepository accountRepository;

    @Override
    public boolean isAssignedToAnyAccount(Long roleId) {
        return accountRepository.existsByRoleId(roleId);
    }
}