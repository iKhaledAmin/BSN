package com.khaled_amin.book_social_network.identity.client.domain.model;



import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.core.model.ActorSource;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "clients")
public class Client extends AuditableEntity implements ActorSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Public identifier used for authentication (machine identifier).
     */
    @Column(name = "client_id", nullable = false, unique = true, updatable = false)
    private String clientId;

    /**
     * Hashed secret used for authentication.
     */
    @Column(name = "secret_hash", nullable = false)
    private String secretHash;


    /**
     * Name of the client (human label).
     */
    @Column(name = "name", nullable = false)
    private String name;


    @Column(name = "description")
    private String description;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ClientStatus status = ClientStatus.ACTIVE;

    @Override
    public ActorIdentity getActorIdentity() {
        return ActorIdentity.of(
                ActorType.CLIENT,
                id.toString()
        );
    }

    public Set<String> getScopes() {
        return new HashSet<>();
    }

    public void activate() { this.status = ClientStatus.ACTIVE; }
    public void disable()  { this.status = ClientStatus.DISABLED; }



}