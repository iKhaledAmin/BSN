package com.khaled_amin.book_social_network.identity.user.role.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import com.khaled_amin.book_social_network.identity.user.role.exception.RoleTechnicalException;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "role_capabilities",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"capability_id", "role_id"})
        }
)
@AttributeOverrides({

        @AttributeOverride(
                name = "createdAt",
                column = @Column(
                        name = "added_at",
                        nullable = false,
                        updatable = false
                )
        ),

        @AttributeOverride(
                name = "createdBy.actorType",
                column = @Column(
                        name = "added_by_actor_type",
                        nullable = false,
                        updatable = false
                )
        ),

        @AttributeOverride(
                name = "createdBy.actorCode.value",
                column = @Column(
                        name = "added_by_actor_code",
                        nullable = false,
                        updatable = false
                )
        )
})
public class RoleCapability extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_capabilities_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "capability_id", nullable = false, updatable = false)
    private Capability capability;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false, updatable = false)
    private Role role;

    // -------------------------------------- Business Methods ---------------------------------- //

    public static RoleCapability create(Capability capability, Role role){
        if (role == null) throw RoleTechnicalException.nullRole();

        if (capability == null) throw RoleTechnicalException.nullCapability();

        return RoleCapability.builder()
                .capability(capability)
                .role(role)
                .build();
    }
}
