package com.khaled_amin.book_social_network.bootstrap;

import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(InitializerOrder.ADMIN)
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {

    @Override
    public void run(@NonNull ApplicationArguments args)  {
        // todo - implement logic to insert the first user (Admin) in DB
    }
}
