package com.khaled_amin.book_social_network.auth.account.application.service;


import com.khaled_amin.book_social_network.auth.account.api.dto.*;
import com.khaled_amin.book_social_network.core.api.ActionResponse;

public interface AccountAuthenticationService {

    void createBootstrapAdmin(String username, String rawPassword, String email);

    AccountRegistrationResponse register(AccountRegistrationRequest request);

    AccountActivationResponse activate(AccountActivationRequest request);

    AccountLoginResponse login(AccountLoginRequest request);

    ActionResponse requestResetPassword(AccountResetPasswordRequest request);

    ActionResponse confirmResetPassword(AccountConfirmResetPasswordRequest request);
}
