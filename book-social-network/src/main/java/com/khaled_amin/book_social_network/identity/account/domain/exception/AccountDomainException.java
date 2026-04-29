package com.khaled_amin.book_social_network.user.domain.exception;

import com.khaled_amin.book_social_network.core.exception.BaseException;

public class AccountDomainException extends BaseException {

    private AccountDomainException(AccountDomainError error, String message) {
        super(error, message);
    }

    // ---------- Generic ---------- //

    public static AccountDomainException of(AccountDomainError error) {
        return new AccountDomainException(error, error.getMessage());
    }

    public static AccountDomainException of(AccountDomainError error, String customMessage) {
        return new AccountDomainException(error, customMessage);
    }

    // ---------- Domain Methods ---------- //

    public static AccountDomainException roleAlreadyAssigned() {
        return of(AccountDomainError.ROLE_ALREADY_ASSIGNED);
    }

    public static AccountDomainException roleNotAssigned() {
        return of(AccountDomainError.ROLE_NOT_ASSIGNED);
    }

    public static AccountDomainException invalidProfile() {
        return of(AccountDomainError.INVALID_PROFILE);
    }

    public static AccountDomainException profileAlreadyAttached() {
        return of(AccountDomainError.PROFILE_ALREADY_ATTACHED);
    }


    public static AccountDomainException invalidFirstName() {
        return of(AccountDomainError.INVALID_FIRST_NAME);
    }

    public static AccountDomainException invalidLastName() {
        return of(AccountDomainError.INVALID_LAST_NAME);
    }

    public static AccountDomainException invalidUsername() {
        return of(AccountDomainError.INVALID_USERNAME);
    }

    public static AccountDomainException invalidPassword() {
        return of(AccountDomainError.INVALID_PASSWORD);
    }

    public static AccountDomainException invalidEmail() {
        return of(AccountDomainError.INVALID_EMAIL);
    }

    public static AccountDomainException invalidAccount() {
        return of(AccountDomainError.INVALID_ACCOUNT);
    }

    public static AccountDomainException invalidRoles() {
        return of(AccountDomainError.INVALID_ROLES);
    }

    public static AccountDomainException invalidRole() {
        return of(AccountDomainError.INVALID_ROLE);
    }

    public static AccountDomainException emptyRoles() {
        return of(AccountDomainError.EMPTY_ROLES);
    }


    public static AccountDomainException missingSystemRole() {
        return of(AccountDomainError.MISSING_SYSTEM_ROLE);
    }

    public static AccountDomainException duplicateRoles() {
        return of(AccountDomainError.DUPLICATE_ROLES);
    }


    public static AccountDomainException invalidPhoneNumber() {
        return of(AccountDomainError.INVALID_PHONE_NUMBER);
    }

    public static AccountDomainException invalidBirthDate() {
        return of(AccountDomainError.INVALID_BIRTH_DATE);
    }

    public static AccountDomainException invalidProfession() {
        return of(AccountDomainError.INVALID_PROFESSION);
    }

    public static AccountDomainException invalidProfileStatus() {
        return of(AccountDomainError.INVALID_PROFILE_STATUS);
    }

    public static AccountDomainException invalidAccountStatus() {
        return of(AccountDomainError.INVALID_ACCOUNT_STATUS);
    }

    public static AccountDomainException invalidAccountId() {
        return of(AccountDomainError.INVALID_ACCOUNT_ID);
    }

    public static AccountDomainException invalidAccountState() {
        return of(AccountDomainError.INVALID_ACCOUNT_STATE);
    }
}