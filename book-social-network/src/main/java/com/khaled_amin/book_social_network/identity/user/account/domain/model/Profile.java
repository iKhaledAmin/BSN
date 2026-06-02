package com.khaled_amin.book_social_network.identity.user.account.domain.model;

import com.khaled_amin.book_social_network.core.audit.AuditableEntity;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.ProfileCreateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.ProfileUpdateCommand;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountTechnicalException;
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
    @Column(name = "profile_status", nullable = false)
    private ProfileStatus profileStatus = ProfileStatus.getDefault();


    // ------------------------------------ Business Methods -------------------------------- //

    public static Profile create(ProfileCreateCommand command) {

        if (command == null){
            throw AccountTechnicalException.nullProfileCreateCommand();
        }

        return Profile.builder()
                .firstName(command.firstName().toString())
                .lastName(command.lastName().toString())
                .profileStatus(ProfileStatus.getDefault())
                .build();
    }

    public void update(ProfileUpdateCommand command) {

        if (command == null) {
            throw AccountTechnicalException.nullProfileUpdateCommand();
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

    }

    @Transient
    public String getFullName() {
        return firstName + " " + lastName;
    }

    // ------------------------------------ End Business Methods -------------------------------- //

}
