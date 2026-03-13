package com.khaled_amin.book_social_network.user.model.entity;

import com.khaled_amin.book_social_network.role.model.entity.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
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

    @PrePersist
    public void applyDefaults() {
        if (assignedAt == null) {
            assignedAt = LocalDateTime.now();
        }
        if (assignedBy == null) {
            assignedBy = "system";
        }
    }
}
