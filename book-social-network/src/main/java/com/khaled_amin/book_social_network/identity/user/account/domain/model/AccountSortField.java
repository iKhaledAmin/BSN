package com.khaled_amin.book_social_network.identity.user.account.domain.model;

import com.khaled_amin.book_social_network.core.pagination.SortField;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum AccountSortField implements SortField {

    USERNAME("username"),
    LAST_LOGIN("lastLogin"),
    CREATED_AT("createdAt");

    private final String field

    ;

    @Override
    public String getField() {
        return field;
    }
}
