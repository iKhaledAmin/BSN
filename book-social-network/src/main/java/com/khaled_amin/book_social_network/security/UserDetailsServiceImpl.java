package com.khaled_amin.book_social_network.security;

import com.khaled_amin.book_social_network.user.application.service.AccountService;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import com.khaled_amin.book_social_network.user.domain.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@AllArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) {
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return SecurityUser.of(
                account.getId(),
                account.getUsername(),
                account.getPassword(),
                account.getAccountStatus().isActive(),
                account.getAccountStatus().isLocked(),
                account.getRoleNames()
        );
    }
}
