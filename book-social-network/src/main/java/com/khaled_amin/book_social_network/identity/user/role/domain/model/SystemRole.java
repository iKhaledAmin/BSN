package com.khaled_amin.book_social_network.identity.user.role.domain.model;

import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDescription;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleDisplayName;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import com.khaled_amin.book_social_network.identity.user.role.exception.RoleTechnicalException;
import lombok.Getter;

import java.util.Arrays;


@Getter
public enum SystemRole {

    SUPER_ADMIN("SUPER_ADMIN",  "Super Administrator", "Full system access", false),
    ADMIN("ADMIN", "Administrator", "System administrator", false),
    USER("USER", "User", "Default system user", true);


    private final RoleName name;
    private final RoleDisplayName displayName;
    private final RoleDescription description;
    private final boolean defaultRole;

    SystemRole(String name,String displayName, String description, boolean defaultRole) {


        this.name = RoleName.of(name);
        this.displayName = RoleDisplayName.of(displayName);
        this.description = RoleDescription.of(description);
        this.defaultRole = defaultRole;
    }

    // Execute validation during enum initialization
    static {
        validateDefaultRole();
    }

    private static void validateDefaultRole() {

        long count = Arrays.stream(values())
                .filter(SystemRole::isDefaultRole)
                .count();

        if (count < 1) {
            throw RoleTechnicalException.invalidSystemRoleConfiguration()
                    .withDebugDetails("reason", "At least one system role must be marked as default")
                    .withDebugDetails("defaultRolesCount", count);
        }
    }
}


