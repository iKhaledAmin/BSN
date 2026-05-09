package com.khaled_amin.book_social_network.identity.user.account.domain.model;

import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.account.domain.exception.AccountDomainException;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "account_roles",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"account_id", "role_id"})}
)
@EntityListeners(AuditingEntityListener.class)
public class AccountRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_role_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false, updatable = false)
    private Account account;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false, updatable = false)
    private Role role;

    @CreatedDate
    @Column(name = "assigned_at", nullable = false, updatable = false)
    private LocalDateTime assignedAt;

    @CreatedBy
    @Column(name = "assigned_by", nullable = false, updatable = false)
    private String assignedBy;

    // -------------------------------------- Business Methods ---------------------------------- //

    public static AccountRole create(Account account, Role role) {

        if (account == null) {
            throw AccountDomainException
                    .invalidAccount()
                    .withDetail("reason", "Account must not be null");
        }

        if (role == null) {
            throw AccountDomainException
                    .invalidRole()
                    .withDetail("reason", "Role must not be null");
        }

        return AccountRole.builder()
                .account(account)
                .role(role)
                .build();
    }
    // ------------------------------------ End Business Methods -------------------------------- //
}
