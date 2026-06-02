package com.khaled_amin.book_social_network.core.pagination;

public enum SortDirection {
    ASC,
    DESC

    ;
    public static SortDirection getDefault() {
        return DESC;
    }
}