package com.khaled_amin.book_social_network.role.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.role.domain.command.UpdateRoleCommand;
import com.khaled_amin.book_social_network.role.domain.exception.RoleDomainException;
import com.khaled_amin.book_social_network.role.domain.value.RoleDescription;
import com.khaled_amin.book_social_network.role.domain.value.RoleDisplayName;
import com.khaled_amin.book_social_network.role.domain.value.RoleName;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "roles")
public class Role extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            updatable = false,
            unique = true,
            columnDefinition = "varchar(50)",
            comment = "Unique identifier of the role. This field is immutable and cannot be changed."
    )
    private String name;

    @Column(
            name = "display_name",
            nullable = false,
            unique = true,
            columnDefinition = "varchar(50)",
            comment = "Human-readable value of the role used for UI and presentation purposes. " +
                    "This field is mutable and can be updated without affecting system behavior."
    )
    private String displayName;

    @Column(
            name = "description",
            nullable = false,
            columnDefinition = "varchar(255)"
    )
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "role_type",
            nullable = false,
            updatable = false,
            comment = "Defines whether the role is system-defined or business-defined: [SYSTEM,BUSINESS] "
    )
    private RoleType roleType;

    @Column(name = "is_default",
            nullable = false,
            columnDefinition = "boolean default false",
            comment = "Indicates whether the role is automatically assigned to newly registered accounts."
    )
    private boolean defaultRole = false;

    @Column(name = "is_protected",
            nullable = false,
            columnDefinition = "boolean default false",
            comment = "Indicates whether the role is protected from modification or deletion. " +
                    "System roles and critical business roles should always be protected."
    )
    private boolean protectedRole = false;
// ------------------------------------ Business Methods -------------------------------- //

    public static Role create(
            RoleName roleName,
            RoleDisplayName displayName,
            RoleDescription description,
            RoleType roleType,
            boolean isDefaultRole,
            boolean isProtectedRole
    ){

        boolean finalProtected = isProtectedRole || isDefaultRole || roleType == RoleType.SYSTEM;

        Role newRole = Role.builder()
                .name(roleName.value())
                .displayName(displayName.value())
                .description(description.value())
                .defaultRole(isDefaultRole)
                .protectedRole(finalProtected)
                .roleType(roleType)
                .build();

        newRole.validateState();
        return newRole;
    }


    public void update(UpdateRoleCommand command) {

        if (command == null) {
            throw RoleDomainException
                    .invalidCommand()
                    .withDetail("reason", "UpdateRoleCommand object must not be null");
        }

        if (command.displayName() != null) {
            this.displayName = command.displayName().value();
        }

        if (command.description() != null) {
            this.description = command.description().value();
        }

        if (command.defaultRole() != null) {
            this.defaultRole = command.defaultRole();
        }

        if (command.protectedRole() != null) {
            this.protectedRole = command.protectedRole();
            validateProtectedRoleViolation();
        }

        validateState();
    }


    public boolean isSystemRole() { return roleType.isSystem();}
    public boolean isBusinessRole(){ return roleType.isBusiness();}

// ------------------------------------ End Business Methods -------------------------------- //


// ------------------------------------ Validation Methods -------------------------------- //


    private void validateState() {
        if (name == null || name.isBlank())
            throw RoleDomainException.invalidRoleState().withDetail("reason", "Role name must not be null or empty");

        if (displayName == null || displayName.isBlank())
            throw RoleDomainException.invalidRoleState().withDetail("reason", "Role display name must not be null or empty");

        if (description == null || description.isBlank())
            throw RoleDomainException.invalidRoleState().withDetail("reason", "Role description must not be null or empty");

        if (roleType == null)
            throw RoleDomainException.invalidRoleType().withDetail("reason", "Role type must not be null");

        if (isSystemRole() && !isProtectedRole())
            throw RoleDomainException.invalidRoleState().withDetail("reason", "System role must be protected");

        if (isDefaultRole() && !isProtectedRole())
            throw RoleDomainException.invalidRoleState().withDetail("reason", "Default role must be protected");

    }

    private void validateProtectedRoleViolation() {
        if (!isProtectedRole() && (isDefaultRole() || isSystemRole()))
            throw RoleDomainException.protectedRoleViolation().withDetail("reason", "This role may be a system role or default role");
    }


// ------------------------------------ End Validation Methods -------------------------------- //

}
