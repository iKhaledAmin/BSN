package com.khaled_amin.book_social_network.identity.user.account.api.dto;

import com.khaled_amin.book_social_network.core.pagination.PageRequest;
import com.khaled_amin.book_social_network.core.pagination.SortField;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.AccountSortField;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AccountPageRequest extends PageRequest {
    private AccountSortField sortBy = AccountSortField.CREATED_AT;

    @Override
    public SortField getSortBy() {
        return sortBy;
    }
}