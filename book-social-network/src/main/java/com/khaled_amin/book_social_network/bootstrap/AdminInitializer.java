package com.khaled_amin.book_social_network.bootstrap;

import com.khaled_amin.book_social_network.common.config.SystemProperties;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import com.khaled_amin.book_social_network.role.model.enums.SystemRoles;
import com.khaled_amin.book_social_network.role.service.RoleService;
import com.khaled_amin.book_social_network.user.factory.AccountFactory;
import com.khaled_amin.book_social_network.user.model.dto.RegistrationRequest;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import com.khaled_amin.book_social_network.user.repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Order(InitializerOrder.ADMIN)
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final AccountRepo accountRepo;
    private final RoleService roleService;
    private final AccountFactory accountFactory;
    private final SystemProperties systemProperties;


    @Override
    @Transactional
    public void run(String... args) {

        String username = systemProperties.getAdmin().getUsername();
        String password = systemProperties.getAdmin().getPassword();
        String email = systemProperties.getAdmin().getEmail();

        if (accountRepo.existsByAccountRolesRoleSystemCode(SystemRoles.ADMIN.getSystemCode())) {
            return;
        }

        Role adminRole = roleService.getBySystemCode(SystemRoles.ADMIN.getSystemCode());

        RegistrationRequest request = RegistrationRequest.builder()
                .firstName("System")
                .lastName("Administrator")
                .username(username)
                .password(password)
                .emailAddress(email)
                .build();

        Account adminAccount = accountFactory.createAccount(request, List.of(adminRole));
        adminAccount.activate();

        accountRepo.save(adminAccount);
    }

}