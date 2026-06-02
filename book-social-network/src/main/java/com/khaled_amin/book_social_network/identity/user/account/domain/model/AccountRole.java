package com.khaled_amin.book_social_network.identity.user.account.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountTechnicalException;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "account_roles",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"account_id", "role_id"})
        }
)

@AttributeOverrides({

        @AttributeOverride(
                name = "createdAt",
                column = @Column(
                        name = "assigned_at",
                        nullable = false,
                        updatable = false
                )
        ),

        @AttributeOverride(
                name = "createdBy.actorType",
                column = @Column(
                        name = "assigned_by_actor_type",
                        nullable = false,
                        updatable = false
                )
        ),

        @AttributeOverride(
                name = "createdBy.actorCode.value",
                column = @Column(
                        name = "assigned_by_actor_code",
                        nullable = false,
                        updatable = false
                )
        )
})
public class AccountRole extends AuditableEntity {

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

    // -------------------------------------- Business Methods ---------------------------------- //

    public static AccountRole create(Account account, Role role) {

        if (account == null) {
            throw AccountTechnicalException.nullAccount();
        }

        if (role == null) {
            throw AccountTechnicalException.nullRole();
        }
        return AccountRole.builder()
                .account(account)
                .role(role)
                .build();
    }
    // ------------------------------------ End Business Methods -------------------------------- //
}
