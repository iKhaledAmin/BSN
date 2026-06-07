package com.khaled_amin.book_social_network.identity.capability.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.identity.capability.domain.definition.CapabilityDefinition;
import com.khaled_amin.book_social_network.identity.capability.exception.CapabilityTechnicalException;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(
        name = "capabilities",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_capability_name_module",
                        columnNames = {"name", "module"}
                )
        }
)
public class Capability extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "capability_id")
    private Long id;

    /**
     * Immutable internal identifier.
     * Example:
     * ROLE_CREATE
     * STOCK_ITEM_CREATE
     */
    @Column(name = "code", nullable = false, updatable = false, unique = true, length = 100)
    private String code;

    /**
     * Protected resource.
     * Example:
     * role
     */
    @Column(name = "resource", nullable = false, updatable = false)
    private String resource;

    /**
     * Allowed action.
     * Example:
     * create
     */
    @Column(name = "action", nullable = false, updatable = false)
    private String action;

    @Column(name = "name", nullable = false, length = 150)
    private String name;

    @Column(name = "description", length = 500)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "module",
            nullable = false,
            updatable = false,
            comment = "The domain module to which the capabilities belongs"
    )
    private SystemDomain module;

    @Column(
            name = "system_managed",
            nullable = false,
            updatable = false,
            comment = "Only the system itself can assign or revoke it"
    )
    private boolean systemManaged;


    public static Capability create(CapabilityDefinition definition) {
        if (definition == null){
            throw CapabilityTechnicalException.nullDefinition();
        }

        return Capability.builder()
                .code(definition.getCode().toString())
                .resource(definition.getResource().toString())
                .action(definition.getAction().toString())
                .name(definition.getName().toString())
                .description(definition.getDescription() != null ? definition.getDescription().toString() : null)
                .module(definition.getModule())
                .systemManaged(definition.isSystemManaged())
                .build();
    }


    /**
     * Converts this capability into its canonical permission representation.
     *
     * <p>
     * The permission representation is used internally by the authorization
     * and security layers for authority evaluation and access control checks.
     * </p>
     *
     * <p>
     * Format:
     * </p>
     * <pre>
     * RESOURCE_ACTION
     * </pre>
     *
     * <p>
     * Examples:
     * </p>
     * <pre>
     * role_create
     * stock_item_update
     * password_reset_confirm
     * </pre>
     *
     * @return canonical permission identifier
     */
    public String toPermission() {
        return resource + "_" + action;
    }

    /**
     * Converts this capability into its canonical OAuth2 scope representation.
     *
     * <p>
     * The scope representation is intended for token-based authorization systems
     * such as OAuth2 and JWT claims.
     * </p>
     *
     * <p>
     * Format:
     * </p>
     * <pre>
     * resource:action
     * </pre>
     *
     * <p>
     * Examples:
     * </p>
     * <pre>
     * role:create
     * stock_item:update
     * password_reset:confirm
     * </pre>
     *
     * @return canonical scope identifier
     */
    public String toScope() {
        return resource + ":" + action;
    }

}