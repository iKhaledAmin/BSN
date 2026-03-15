package com.khaled_amin.book_social_network.bootstrap;

import com.khaled_amin.book_social_network.role.model.entity.Role;
import com.khaled_amin.book_social_network.role.model.enums.SystemRoles;
import com.khaled_amin.book_social_network.role.repository.RoleRepo;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
@Order(InitializerOrder.ROLE)
@RequiredArgsConstructor
public class RoleInitializer implements CommandLineRunner {

    private final RoleRepo roleRepo;

    @Override
    public void run(String @NonNull ... args) {

        List<Role> rolesToInsert = new ArrayList<>();

        for (SystemRoles systemRole : SystemRoles.values()) {

            String systemCode = systemRole.getSystemCode();

            if (!roleRepo.existsBySystemCode(systemCode)) {

                Role newRole = Role.builder()
                        .name(systemRole.getName())
                        .description(systemRole.getDescription())
                        .systemCode(systemCode)
                        .defaultRole(systemRole.isDefaultRole())
                        .protectedRole(true)
                        .build();

                rolesToInsert.add(newRole);
            }
        }

        if (!rolesToInsert.isEmpty()) {
            roleRepo.saveAll(rolesToInsert);
        }
    }
}
