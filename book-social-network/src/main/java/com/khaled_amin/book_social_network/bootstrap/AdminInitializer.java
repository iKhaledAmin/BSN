package com.khaled_amin.book_social_network.bootstrap;

import com.khaled_amin.book_social_network.core.logging.audit.BusinessEventLogger;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountCreateRequest;
import com.khaled_amin.book_social_network.identity.user.account.application.service.AccountService;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Order(InitializerOrder.ADMIN)
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final AccountService accountService;
    private final BootstrapProperties properties;
    private final BusinessEventLogger businessEventLogger;

    @Override
    public void run(String... args) {

        RoleName superAdminRoleName = SystemRole.SUPER_ADMIN.getName();

        if (accountService.existsByRoleName(superAdminRoleName)) {
            return;
        }

        AccountCreateRequest request = AccountCreateRequest.builder()
                .username(properties.admin().username())
                .password(properties.admin().password())
                .emailAddress(properties.admin().email())
                .firstName("System")
                .lastName("Administrator")
                .roleNames(List.of(superAdminRoleName.toString()))
                .build();

        Account account = accountService.create(request);

        accountService.activate(account.getAccountCode());

        businessEventLogger.systemAdminInitialized(
                account.getActorCode().toString(),
                properties.admin().username()
        );
    }
}