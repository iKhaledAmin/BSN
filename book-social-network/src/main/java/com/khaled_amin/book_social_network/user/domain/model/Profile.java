package com.khaled_amin.book_social_network.user.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.user.domain.command.ProfileUpdateCommand;
import com.khaled_amin.book_social_network.user.domain.exception.AccountDomainException;
import com.khaled_amin.book_social_network.user.domain.value.FirstName;
import com.khaled_amin.book_social_network.user.domain.value.LastName;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter(AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(
            name = "profile_status",
            nullable = false,
            columnDefinition = "VARCHAR(20) DEFAULT 'GUEST'"
    )
    private ProfileStatus profileStatus = ProfileStatus.getDefault();


    // ------------------------------------ Business Methods -------------------------------- //

    public static Profile create(FirstName firstName,LastName lastName) {

        Profile profile = Profile.builder()
                .firstName(firstName.value())
                .lastName(lastName.value())
                .profileStatus(ProfileStatus.getDefault())
                .build();

        profile.validateState();

        return profile;
    }

    public void update(ProfileUpdateCommand command) {

        if (command == null) {
            throw AccountDomainException
                    .invalidProfile()
                    .withDetail("reason", "Update profile command must not be null");
        }

        command.firstName()
                .ifPresent(fn -> this.firstName = fn.value());

        command.lastName()
                .ifPresent(ln -> this.lastName = ln.value());

        command.gender()
                .ifPresent(g -> this.gender = g);

        command.birthDate()
                .ifPresent(bd -> this.birthDate = bd.value());

        command.phoneNumber()
                .ifPresent(p -> this.phoneNumber = p.value());

        command.profession()
                .ifPresent(p -> this.profession = p.value());

        this.validateState();
    }

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }


    private void validateState() {
        if (firstName == null || firstName.isBlank()) {
            throw AccountDomainException
                    .invalidFirstName()
                    .withDetail("reason", "First value must not be null or blank");
        }

        if (lastName == null || lastName.isBlank()) {
            throw AccountDomainException
                    .invalidLastName()
                    .withDetail("reason", "Last value must not be null or blank");
        }

        if (profileStatus == null){
            throw AccountDomainException
                    .invalidProfileStatus()
                    .withDetail("reason", "Profile status must not be null");
        }
    }
    // ------------------------------------ End Business Methods -------------------------------- //

}
