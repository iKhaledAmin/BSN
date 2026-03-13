package com.khaled_amin.book_social_network.user.model.entity;

import com.khaled_amin.book_social_network.audit.AuditableEntity;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import com.khaled_amin.book_social_network.user.model.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "accounts")
public class Account extends AuditableEntity implements UserDetails,Principal {

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
    @Column(
            name = "account_status",
            nullable = false,
            columnDefinition = "VARCHAR(20) DEFAULT 'DISABLED'"
    )
    private AccountStatus accountStatus = AccountStatus.DISABLED;


    // -------------------------------------- Relationships ----------------------------------- //

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;


    @Builder.Default
    @OneToMany(
            mappedBy = "account",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<AccountRole> accountRoles = new ArrayList<>();
    // ------------------------------------ End Relationships -------------------------------- //

    @PrePersist
    public void applyDefaults() {
        if (accountStatus == null) {
            accountStatus = AccountStatus.DISABLED;
        }
    }

    public void attachProfile(Profile profile) {
        this.profile = profile;
    }

    public void assignRole(Role role) {
        AccountRole accountRole = AccountRole.builder()
                .account(this)
                .role(role)
                .build();

        accountRoles.add(accountRole);
    }

    public List<Role> getRoles() {
        return accountRoles.stream()
                .map(AccountRole::getRole)
                .collect(Collectors.toList());
    }


    public boolean hasRole(String roleName) {
        return getRoles().stream()
                .anyMatch(r -> r.getName().equals(roleName));
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountStatus != AccountStatus.LOCKED;
    }

    @Override
    public boolean isEnabled() {
        return accountStatus == AccountStatus.ACTIVE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                //.map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }


}
