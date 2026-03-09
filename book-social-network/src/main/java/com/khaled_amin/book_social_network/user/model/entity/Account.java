package com.Khaled_Amin.book_social_network.user.model.entity;

import com.Khaled_Amin.book_social_network.audit.AuditableEntity;
import com.Khaled_Amin.book_social_network.role.model.entity.Role;
import com.Khaled_Amin.book_social_network.user.model.enums.AccountStatus;
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

    @Column(name = "username",unique = true, nullable = false, updatable = false)
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
    private AccountStatus status = AccountStatus.DISABLED;


    // -------------------------------------- Relationships ----------------------------------- //

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "profile_id", nullable = false)
    private Profile profile;


    @Builder.Default
    @OneToMany(
            mappedBy = "user",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY
    )
    private List<AccountRole> accountRoles = new ArrayList<>();
    // ------------------------------------ End Relationships -------------------------------- //

    @PrePersist
    public void applyDefaults() {
        if (status == null) {
            status = AccountStatus.DISABLED;
        }
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
        return status != AccountStatus.LOCKED;
    }

    @Override
    public boolean isEnabled() {
        return status == AccountStatus.ACTIVE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getRoles().stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                //.map(role -> new SimpleGrantedAuthority(role.getName()))
                .toList();
    }


}
