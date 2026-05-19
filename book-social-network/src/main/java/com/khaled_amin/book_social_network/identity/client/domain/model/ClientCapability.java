package com.khaled_amin.book_social_network.identity.client.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.identity.capability.domain.model.Capability;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "client_capabilities",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"capability_id", "client_id"})
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
public class ClientCapability extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "client_capabilities_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "capability_id", nullable = false, updatable = false)
    private Capability capability;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "client_id", nullable = false, updatable = false)
    private Client client;

    // -------------------------------------- Business Methods ---------------------------------- //

    public static ClientCapability create(Capability capability,Client client){
//        if (client == null)
//           // throw .invalid().withDetail("reason", "Client cannot be null");
//
//        if (capability == null)
//            //throw .invalid().withDetail("reason", "Capability cannot be null");

        return ClientCapability.builder()
                .capability(capability)
                .client(client)
                .build();
    }
}