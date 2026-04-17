package com.khaled_amin.book_social_network.user.application.service;

import com.khaled_amin.book_social_network.role.application.service.RoleUsageService;
import com.khaled_amin.book_social_network.user.domain.repository.AccountRepository;
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