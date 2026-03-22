package com.khaled_amin.book_social_network.authorization.policy.base;

public interface Policy<C extends PolicyContext> {

    void check(C context);

}