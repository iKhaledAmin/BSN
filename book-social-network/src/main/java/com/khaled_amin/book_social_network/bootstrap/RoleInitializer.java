package com.khaled_amin.book_social_network.bootstrap;

import com.khaled_amin.book_social_network.role.model.dto.RoleRequest;
import com.khaled_amin.book_social_network.role.model.enums.DefaultRoles;
import com.khaled_amin.book_social_network.role.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Component
@Order(InitializerOrder.ROLE)
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleService roleService;

    @Override
    public void run(String @NonNull ... args) {

        for (DefaultRoles role : DefaultRoles.values()) {

            String roleName = role.name();

            if (!roleService.existsByName(roleName)) {

                RoleRequest request = RoleRequest.builder()
                        .name(roleName)
                        .description(role.getDescription())
                        .build();

                roleService.add(request);
            }
        }
    }
}





