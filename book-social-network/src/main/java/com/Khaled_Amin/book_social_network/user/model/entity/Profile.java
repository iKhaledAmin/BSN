package com.Khaled_Amin.book_social_network.user.model.entity;

import com.Khaled_Amin.book_social_network.audit.AuditableEntity;
import com.Khaled_Amin.book_social_network.user.model.enums.Gender;
import com.Khaled_Amin.book_social_network.user.model.enums.ProfileStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "profiles")
public class Profile extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long id;

    @Column(name = "first_name" ,nullable = false)
    private String firstName;

    @Column(name = "last_name" ,nullable = false)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "profession")
    private String profession;

    @Enumerated(EnumType.STRING)
    @Column(
            name = "profile_status",
            nullable = false,
            columnDefinition = "VARCHAR(20) DEFAULT 'GUEST'"
    )
    private ProfileStatus profileStatus = ProfileStatus.GUEST;

    @PrePersist
    public void applyDefaults() {
        if (profileStatus == null) {
            profileStatus = ProfileStatus.GUEST;
        }
    }

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

}
