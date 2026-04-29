package com.khaled_amin.book_social_network.email.domain.model;

import lombok.Getter;

@Getter
public enum EmailTemplate {

    ACCOUNT_ACTIVATION("account_activation","Activate your account"),
    RESET_PASSWORD("account_activation","Reset you password")
    ;



    private final String name;
    private final String subject;

    EmailTemplate(String name,String subject) {
        this.name = name;
        this.subject = subject;
    }
}
