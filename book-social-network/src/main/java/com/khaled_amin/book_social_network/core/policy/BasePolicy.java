package com.khaled_amin.book_social_network.core.policy;


public interface BasePolicy<C extends BasePolicyContext> {

    void check(C context);

    void validateContext(C context);


}