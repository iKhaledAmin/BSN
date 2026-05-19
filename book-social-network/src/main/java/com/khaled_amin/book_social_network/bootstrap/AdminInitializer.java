package com.khaled_amin.book_social_network.bootstrap;

import com.khaled_amin.book_social_network.auth.account.application.port.in.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(InitializerOrder.ADMIN)
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    private final AuthenticationService authenticationService;
    private final BootstrapProperties properties;

    @Override
    public void run(String... args) {

        authenticationService.createBootstrapAdmin(
                properties.admin().username(),
                properties.admin().password(),
                properties.admin().email()
        );
    }
}