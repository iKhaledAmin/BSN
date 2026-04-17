package com.khaled_amin.book_social_network.bootstrap;

import com.khaled_amin.book_social_network.config.SystemProperties;
import com.khaled_amin.book_social_network.role.application.service.RoleService;
import com.khaled_amin.book_social_network.role.domain.model.Role;
import com.khaled_amin.book_social_network.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.user.application.service.AccountService;
import com.khaled_amin.book_social_network.user.domain.command.AccountCreateCommand;
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

    private final AccountService accountService;
    private final RoleService roleService;
    private final SystemProperties systemProperties;


    @Override
    @Transactional
    public void run(String... args) {

        String username = systemProperties.getAdmin().getUsername();
        String password = systemProperties.getAdmin().getPassword();
        String email = systemProperties.getAdmin().getEmail();

        if (accountService.existsByRoleName(SystemRole.SUPER_ADMIN.getName().value())) {
            return;
        }
        Role adminRole = roleService.getByName(SystemRole.SUPER_ADMIN.getName().value());
        List<Long> roleIds = List.of(adminRole.getId());


        AccountCreateCommand command = AccountCreateCommand.of(
                username,
                password,
                email,
                "System",
                "Admin"
        );

        accountService.create(command, roleIds);
    }

}