package com.khaled_amin.book_social_network.identity.user.account.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorSource;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountBusinessException;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountTechnicalException;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountUpdateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.EncodedPassword;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "accounts")
public class Account extends AuditableEntity implements ActorSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long id;

    @Column(name = "username", nullable = false, updatable = false,unique = true)
    private String username;

    @Column(name = "password_hash", nullable = false)
    private String password;

    @Column(name = "email_address",nullable = false, unique = true)
    private String emailAddress;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "account_status", nullable = false)
    private AccountStatus accountStatus = AccountStatus.getDefault();




    @Embedded
    @AttributeOverride(
            name = "value",
            column = @Column(
                    name = "account_code",
                    nullable = false,
                    updatable = false,
                    unique = true,
                    comment = "Stable globally unique business identity"
            )
    )
    private ActorCode accountCode;


    // -------------------------------------- Relationships ----------------------------------- //

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;

    @Builder.Default
    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private Set<AccountRole> accountRoles = new HashSet<>();
    // ------------------------------------ End Relationships -------------------------------- //


    // ------------------------------------- Actor Source ------------------------------------- //

    @Override
    public ActorType getActorType() {
        return ActorType.ACCOUNT;
    }

    @Override
    public ActorCode getActorCode() {
        return accountCode;
    }

    // ------------------------------------- End Actor Source ---------------------------------- //



    // ------------------------------------ Business Methods -------------------------------- //

    public static Account create(AccountCreateCommand command, Profile profile, List<Role> roles){

        if (command == null){
            throw AccountTechnicalException.nullAccountCreateCommand();
        }

        if (profile == null) {
            throw AccountTechnicalException.nullProfile();
        }

        if (roles == null) {
            throw AccountTechnicalException.nullRoleList();
        }

        if (roles.isEmpty()) {
            throw AccountBusinessException.emptyRoleList()
                    .withClientDetails("reason", "Account must have at least one role");
        }


        Account account = Account.builder()
                .accountCode(command.accountCode())
                .username(command.username().toString())
                .password(command.encodedPassword().toString())
                .emailAddress(command.emailAddress().toString())
                .accountStatus(AccountStatus.getDefault())
                .build();


        account.attachProfile(profile);
        account.assignRoles(roles);

        account.enforceInvariants();

        return account;


    }

    public void update(AccountUpdateCommand command) {

        if (command == null) {
            throw AccountTechnicalException.nullAccountUpdateCommand();
        }

        // Update emailAddress
        command.email()
                .ifPresent(e -> this.emailAddress = e.value());

        // Delegate to Profile
        command.profileCommand()
                .ifPresent(pc -> this.profile.update(pc));

        enforceInvariants();
    }

    private void attachProfile(Profile profile) {

        validateCanAttachProfile(profile);

        this.setProfile(profile);
    }

    public void assignRole(Role role) {

        validateCanAssignRole(role);

        AccountRole accountRole = AccountRole.create(this, role);

        accountRoles.add(accountRole);
    }

    public void assignRoles(List<Role> roles) {
        roles.forEach(this::assignRole);
    }

    public void replaceRoles(List<Role> roles) {

        validateCanReplaceRoles(roles);

        this.accountRoles.clear();

        roles.forEach(this::assignRole);

        this.enforceInvariants();
    }

    public void removeRole(Role role) {

        validateCanRemoveRole(role);

        accountRoles.removeIf(ar -> ar.getRole().getId().equals(role.getId()));

        enforceInvariants();
    }


    public Set<Role> getRoles() {
        return accountRoles.stream()
                .map(AccountRole::getRole)
                .collect(Collectors.toSet());
    }

    public Set<String> getRoleNames(){
        return getRoles()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());
    }


    public boolean hasRole(String roleName) {
        if (roleName == null) {
            return false;
        }

        return getRoles()
                .stream()
                .anyMatch(r -> r.getName().equals(roleName));
    }

    public Set<Capability> getCapabilities() {
        return getRoles()
                .stream()
                .flatMap(role -> role.getCapabilities().stream())
                .collect(Collectors.toSet());
    }

    public Set<String> getPermissions() {
        return getCapabilities()
                .stream()
                .map(Capability::toPermission)
                .collect(Collectors.toSet());
    }

    public void resetPassword(EncodedPassword newPassword) {
        if (this.password.equals(newPassword.value())) {
            throw AccountBusinessException.passwordResetNotAllowed();
        }
        this.password = newPassword.value();
    }

    public void login() {
        lastLogin = LocalDateTime.now();
    }

    public void activate() { setAccountStatus(AccountStatus.ACTIVE); }
    public void lock()     { setAccountStatus(AccountStatus.LOCKED); }
    public void suspend()  { setAccountStatus(AccountStatus.SUSPENDED); }
    public void disable()  { setAccountStatus(AccountStatus.DISABLED); }

    // ------------------------------------ End Business Methods -------------------------------- //


    // ------------------------------------ Validation Methods -------------------------------- //

    private void enforceInvariants() {

        validateSystemRoleExists();
        validateNoDuplicateRoles();
    }

    private void validateSystemRoleExists() {

        boolean hasSystemRole = this.accountRoles
                .stream()
                .map(AccountRole::getRole)
                .anyMatch(role -> role.getRoleType().isSystem());

        if (!hasSystemRole) {
            throw AccountBusinessException.missingSystemRole();
        }
    }

    private void validateNoDuplicateRoles() {

        Set<String> seen = new HashSet<>();

        for (AccountRole accountRole : accountRoles) {

            Role role = accountRole.getRole();

            String roleName = role.getName();

            if (!seen.add(roleName)) {
                throw AccountBusinessException.duplicateRoles()
                        .withClientDetails("roleName", roleName);
            }
        }
    }


    private void validateCanAttachProfile(Profile profile) {

        if (profile == null) {
            throw AccountTechnicalException.nullProfile();
        }

        if (this.profile != null) {
            throw AccountTechnicalException.profileAlreadyAttached();
        }
    }

    private void validateCanAssignRole(Role role){
        if (role == null) {
            throw AccountTechnicalException.nullRole();
        }

        if (this.hasRole(role.getName())) {
            throw AccountBusinessException.roleAssignNotAllowed()
                    .withClientDetails("reason", "Role already assigned to this account")
                    .withClientDetails("roleName", role.getName());
        }
    }

    private void validateCanRemoveRole(Role role) {

        if (role == null || role.getId() == null) {
            throw AccountTechnicalException.nullRole();
        }

        if (!this.hasRole(role.getName())) {
            throw AccountBusinessException.roleRemovalNotAllowed()
                    .withClientDetails("reason", "This account does not have this role")
                    .withClientDetails("roleName", role.getName());
        }

        boolean hasAnotherRole = false;
        boolean hasAnotherSystemRole = false;

        for (AccountRole ar : this.accountRoles) {

            Role currentRole = ar.getRole();

            if (Objects.equals(currentRole.getName(), role.getName())) {
                continue;
            }

            hasAnotherRole = true;

            if (currentRole.getRoleType().isSystem()) {
                hasAnotherSystemRole = true;
            }
        }

        if (!hasAnotherRole) {
            throw AccountBusinessException.roleRemovalNotAllowed()
                    .withClientDetails("reason", "Account must contain at least one role");
        }

        if (!hasAnotherSystemRole) {
            throw AccountBusinessException.roleRemovalNotAllowed()
                    .withClientDetails("reason", "Account must contain at least one system role");
        }
    }


    private static void validateCanReplaceRoles(List<Role> newRoles) {

        if (newRoles == null) {
            throw AccountTechnicalException.nullRoleList();
        }

        if (newRoles.isEmpty()) {
            throw AccountBusinessException.emptyRoleList()
                    .withClientDetails("reason", "Account must have at least one role");
        }

        boolean hasSystemRole = false;
        Set<String> uniqueRoleNames = new HashSet<>();

        for (Role role : newRoles) {

            if (role == null) {
                throw AccountTechnicalException.nullRole();
            }

            String roleName = role.getName();


            if (!uniqueRoleNames.add(roleName)) {
                throw AccountBusinessException.duplicateRoles()
                        .withClientDetails("roleName", roleName);
            }

            if (role.getRoleType().isSystem()) {
                hasSystemRole = true;
            }
        }

        if (!hasSystemRole) {
            throw AccountBusinessException.missingSystemRole();
        }
    }


    // ------------------------------------ End Validation Methods -------------------------------- //

}
