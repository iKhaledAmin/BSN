package com.khaled_amin.book_social_network.role.model.entity;


import com.khaled_amin.book_social_network.audit.AuditableEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long id;

    @Column(name = "role_name", nullable = false, unique = true)
    private String name;

    @Column(name = "role_description", nullable = false)
    private String description;


    @Column(name = "system_code",
            updatable = false,
            unique = true,
            columnDefinition = "varchar(50) default null",
            comment = "Internal identifier for predefined system roles. Null indicates a business-defined role created dynamically by administrators."
    )
    private String systemCode = null;

    @Column(name = "is_default",
            nullable = false,
            columnDefinition = "boolean default false",
            comment = "Indicates whether the role is automatically assigned to newly registered accounts."
    )
    private boolean defaultRole = false;

    @Column(name = "is_protected",
            nullable = false,
            columnDefinition = "boolean default false",
            comment = "Prevents deletion or modification of the role to protect critical system or administrator-defined roles."
    )
    private boolean protectedRole = false;
// ------------------------------------ Business Methods -------------------------------- //

// ------------------------------------ End Business Methods -------------------------------- //
}
