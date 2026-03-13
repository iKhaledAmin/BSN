package com.khaled_amin.book_social_network.security;

import com.khaled_amin.book_social_network.user.model.entity.Account;

public interface CurrentUserService {

    Account getCurrentAccount();

    Long getCurrentAccountId();

    String getCurrentUsername();
}
