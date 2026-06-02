package com.khaled_amin.book_social_network.identity.user.role.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.RoleCreateCommand;
import com.khaled_amin.book_social_network.identity.user.role.domain.command.RoleUpdateCommand;
import com.khaled_amin.book_social_network.identity.user.role.exception.RoleBusinessException;
import com.khaled_amin.book_social_network.identity.user.role.exception.RoleTechnicalException;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


@Getter
@Setter(AccessLevel.PRIVATE)
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

    // -------------------------------------- Relationships ----------------------------------- //

    @Builder.Default
    @OneToMany(
            mappedBy = "role",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<RoleCapability> roleCapabilities = new HashSet<>();
    // ------------------------------------ End Relationships -------------------------------- //

// ------------------------------------ Business Methods -------------------------------- //

    public static Role create(RoleCreateCommand command){
        if (command == null) {
            throw RoleTechnicalException.nullCreateCommand();
        }

        ensureCanCreate(command);

        Role newRole = Role.builder()
                .name(command.name().toString())
                .displayName(command.displayName().toString())
                .description(command.description().toString())
                .defaultRole(command.defaultRole())
                .protectedRole(command.protectedRole())
                .roleType(command.roleType())
                .build();

        newRole.enforceInvariants();
        return newRole;
    }


    public void update(RoleUpdateCommand command) {

        if (command == null) {
            throw RoleTechnicalException.nullUpdateCommand();
        }

        ensureCanUpdate(command);

        command.displayName().ifPresent(displayName -> this.displayName = displayName.toString() );
        command.description().ifPresent(description -> this.description = description.toString() );
        command.defaultRole().ifPresent(defaultRole -> this.defaultRole = defaultRole );
        command.protectedRole().ifPresent(protectedRole -> this.protectedRole = protectedRole );

        enforceInvariants();

    }

    public void delete(){
        ensureCanDelete(this);
    }

    public void removeCapability(Capability capability){
        if (capability == null) {
            throw RoleTechnicalException.nullCapability();
        }

        ensureCanRemoveCapability(capability);

        roleCapabilities.removeIf(rc -> rc.getCapability().equals(capability));
    }

    public void addCapability(Capability capability){
        if (capability == null) {
            throw RoleTechnicalException.nullCapability();
        }

        ensureCanAddCapability(capability);

        RoleCapability roleCapability = RoleCapability.create(capability,this);

        roleCapabilities.add(roleCapability);
    }

    public Set<Capability> getCapabilities() {
        return roleCapabilities.stream()
                .map(RoleCapability::getCapability)
                .collect(Collectors.toSet());
    }


    public Set<String> getPermissions(){
        return roleCapabilities
                .stream()
                .map(roleCapability -> roleCapability.getCapability().toPermission())
                .collect(Collectors.toSet());
    }

    public boolean hasCapability(String capabilityCode) {
        if (capabilityCode == null) {
            return false;
        }

        return getCapabilities()
                .stream()
                .anyMatch(c -> c.getCode().equals(capabilityCode));
    }


// ------------------------------------ End Business Methods -------------------------------- //


// ------------------------------------ Validation Methods -------------------------------- //


    private void enforceInvariants() {

        if (roleType.isSystem() && !isProtectedRole()) {
            throw RoleBusinessException.systemRoleMustBeProtected()
                    .withDebugDetails("roleName", name);
        }

        if (isDefaultRole() && !isProtectedRole()) {
            throw RoleBusinessException.defaultRoleMustBeProtected()
                    .withDebugDetails("roleName", name);
        }
    }

    private static void ensureCanCreate(RoleCreateCommand command) {
        if (command.defaultRole() && !command.protectedRole()) {
            throw RoleBusinessException.defaultRoleMustBeProtected();
        }

        if (command.roleType().isSystem() && !command.protectedRole()) {
            throw RoleBusinessException.systemRoleMustBeProtected();
        }
    }

    private void ensureCanUpdate(RoleUpdateCommand command){

        if (command.protectedRole().isPresent() && command.defaultRole().isPresent()) {
            boolean protectedRole = command.protectedRole().get();
            boolean defaultRole = command.defaultRole().get();

            if (defaultRole && !protectedRole) {
                throw RoleBusinessException.defaultRoleMustBeProtected();
            }
        }

        if(roleType.isSystem()){
            throw RoleBusinessException.systemRoleCannotBeModified();
        }
    }

    private void ensureCanDelete(Role role) {
        if (role.isProtectedRole())
            throw RoleBusinessException.protectedRoleCannotBeDeleted()
                    .withDebugDetails("roleName", role.getName());
    }

    private void ensureCanAddCapability(Capability capability){

        if (this.hasCapability(capability.getCode())) {
            throw RoleBusinessException.capabilityAlreadyAssigned()
                    .withClientDetails("capabilityCode", capability.getCode())
                    .withDebugDetails("roleName",this.name);
        }
    }

    private void ensureCanRemoveCapability(Capability capability) {

        if (!this.hasCapability(capability.getCode())) {
            throw RoleBusinessException.capabilityNotAssigned()
                    .withClientDetails("capabilityCode", capability.getCode())
                    .withDebugDetails("roleName",this.name);
        }
    }



// ------------------------------------ End Validation Methods -------------------------------- //

}
