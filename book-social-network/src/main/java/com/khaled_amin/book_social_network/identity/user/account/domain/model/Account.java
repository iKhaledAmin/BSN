package com.khaled_amin.book_social_network.identity.user.account.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorSource;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountUpdateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.exception.AccountDomainException;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.Email;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.EncodedPassword;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.Username;
import jakarta.persistence.*;
import lombok.*;

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


    @Override
    public ActorType getActorType() {
        return ActorType.ACCOUNT;
    }

    @Override
    public ActorCode getActorCode() {
        return accountCode;
    }



    // ------------------------------------ Business Methods -------------------------------- //

    public static Account create(
              Username username,
              EncodedPassword encodedPassword,
              Email emailAddress,
              ActorCode accountCode,
              Profile profile,
              List<Role> roles
    ) {

        if (roles == null || roles.isEmpty()) {
            throw AccountDomainException
                    .invalidRoles()
                    .withDetail("reason", "roles cannot be null or empty");
        }

        if (profile == null){
            throw AccountDomainException
                    .invalidProfile()
                    .withDetail("reason", "profile cannot be null");
        }


        Account account = Account.builder()
                .username(username.value())
                .password(encodedPassword.value())
                .emailAddress(emailAddress.value())
                .accountCode(accountCode)
                .accountStatus(AccountStatus.getDefault())
                .build();


        account.attachProfile(profile);
        account.assignRoles(roles);

        account.validateState();

        return account;


    }


    public void update(AccountUpdateCommand command) {

        if (command == null) {
            throw AccountDomainException
                    .invalidAccount()
                    .withDetail("reason", "Update command cannot be null");
        }

        // Update email
        command.email()
                .ifPresent(e -> this.emailAddress = e.value());

        // Delegate to Profile
        command.profileCommand()
                .ifPresent(pc -> this.profile.update(pc));

        validateState();
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

        this.validateState();
    }

    public void removeRole(Role role) {

        validateCanRemoveRole(role);

        accountRoles.removeIf(ar -> ar.getRole().getId().equals(role.getId()));

        validateState();
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
            throw AccountDomainException.samePassword()
                    .withDetail("reason", "New password cannot be the same as the old password");
        }
        this.password = newPassword.value();
    }

    public void activate() { setAccountStatus(AccountStatus.ACTIVE); }
    public void lock()     { setAccountStatus(AccountStatus.LOCKED); }
    public void suspend()  { setAccountStatus(AccountStatus.SUSPENDED); }
    public void disable()  { setAccountStatus(AccountStatus.DISABLED); }

    // ------------------------------------ End Business Methods -------------------------------- //


    // ------------------------------------ Validation Methods -------------------------------- //

    private void validateState(){

        if (username == null || username.isBlank()){
            throw AccountDomainException.invalidAccountState().withDetail("reason", "Username must be not be null or empty");
        }

        if (emailAddress == null || emailAddress.isBlank()) {
            throw AccountDomainException.invalidAccountState().withDetail("reason", "Email must be not be null or empty");
        }

        if (password == null || password.isBlank()) {
            throw AccountDomainException.invalidAccountState().withDetail("reason", "Password must be not be null or empty");
        }

        if (accountCode == null) {
            throw AccountDomainException.invalidAccountState().withDetail("reason", "Account code must not be null");
        }

        if (profile == null) {
            throw AccountDomainException.invalidAccountState().withDetail("reason", "Profile must not be null");
        }

        if(accountStatus == null){
            throw AccountDomainException.invalidAccountState().withDetail("reason", "Account status must not be null");
        }

        validateAccountRolesState();
    }

    private void validateAccountRolesState() {
        Set<Role> roles = this.getRoles();

        if (roles == null || roles.isEmpty()) {
            throw AccountDomainException.invalidAccountState().withDetail("reason", "Account roles must not be null or empty");
        }

        boolean hasSystemRole = false;
        Set<String> uniqueRoleNames = new HashSet<>();

        for (Role role : roles) {

            if (role == null) {
                throw AccountDomainException.invalidAccountState().withDetail("reason", "Account role must not be null");
            }

            String roleName = role.getName();

            if (roleName == null || roleName.isBlank()) {
                throw AccountDomainException.invalidAccountState().withDetail("reason", "Account role name must be not null or empty");
            }

            if (!uniqueRoleNames.add(roleName)) {
                throw AccountDomainException.invalidAccountState()
                        .withDetail("roleName", roleName)
                        .withDetail("reason", "Duplicate role name");
            }

            if (role.isSystemRole()) {
                hasSystemRole = true;
            }
        }

        if (!hasSystemRole) {
            throw AccountDomainException
                    .invalidAccountState().withDetail("reason", "Account must have at least one system role");
        }

    }


    private void validateCanAttachProfile(Profile profile){

        if (profile == null) {
            throw AccountDomainException
                    .invalidProfile()
                    .withDetail("reason", "Profile must not be null");
        }

        if (this.getProfile() != null) {
            throw AccountDomainException.profileAlreadyAttached();
        }
    }

    private void validateCanAssignRole(Role role){
        if (role == null) {
            throw AccountDomainException.invalidRole();
        }

        if (this.hasRole(role.getName())) {
            throw AccountDomainException
                    .roleAlreadyAssigned()
                    .withDetail("roleName", role.getName());
        }
    }

    private void validateCanRemoveRole(Role role) {

        if (role == null || role.getId() == null) {
            throw AccountDomainException
                    .invalidRole()
                    .withDetail("reason", "Account role must not be null");
        }

        if (!this.hasRole(role.getName())) {
            throw AccountDomainException
                    .roleNotAssigned()
                    .withDetail("reason", "This account does not have this role")
                    .withDetail("roleName",role.getName());
        }

        boolean hasAnotherRole = false;
        boolean hasAnotherSystemRole = false;

        for (AccountRole ar : this.accountRoles) {
            Role r = ar.getRole();

            if (Objects.equals(r.getName(), role.getName())) {
                continue; // simulate removal
            }

            hasAnotherRole = true;

            if (r.isSystemRole()) {
                hasAnotherSystemRole = true;
            }
        }

        if (!hasAnotherRole) {
            throw AccountDomainException.emptyRoles();
        }

        if (!hasAnotherSystemRole) {
            throw AccountDomainException.missingSystemRole();
        }
    }


    private static void validateCanReplaceRoles(List<Role> newRoles) {

        if (newRoles == null || newRoles.isEmpty()) {
            throw AccountDomainException
                    .invalidRoles()
                    .withDetail("reason", "Account roles must not be null or empty");
        }

        boolean hasSystemRole = false;
        Set<String> uniqueRoleNames = new HashSet<>();

        for (Role role : newRoles) {

            if (role == null) {
                throw AccountDomainException
                        .invalidRole()
                        .withDetail("reason", "Account role must not be null");
            }

            String roleName = role.getName();

            if (roleName == null || roleName.isBlank()) {
                throw AccountDomainException
                        .invalidRole()
                        .withDetail("reason", "Account role value must be not null or empty");
            }

            if (!uniqueRoleNames.add(roleName)) {
                throw AccountDomainException
                        .duplicateRoles()
                        .withDetail("roleName", roleName);
            }

            if (role.isSystemRole()) {
                hasSystemRole = true;
            }
        }

        if (!hasSystemRole) {
            throw AccountDomainException.missingSystemRole();
        }
    }


    // ------------------------------------ End Validation Methods -------------------------------- //

}
