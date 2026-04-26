package com.khaled_amin.book_social_network.email.domain.model;

public enum EmailStatus {
    PENDING,
    SENT,
    FAILED,
    RETRYING;

    public static EmailStatus getDefaultValue(){
        return PENDING;
    }

    public boolean isSent(){return this == SENT;}
    public boolean isFailed(){return this == FAILED;}
    public boolean isPending(){return this == PENDING;}
    public boolean isRetrying(){return this == RETRYING;}


}