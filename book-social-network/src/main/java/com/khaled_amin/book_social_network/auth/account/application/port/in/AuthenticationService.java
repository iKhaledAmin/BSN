package com.khaled_amin.book_social_network.auth.account.application.port.in;


import com.khaled_amin.book_social_network.auth.account.api.dto.*;
import com.khaled_amin.book_social_network.core.api.ActionResponse;

public interface AuthenticationService {

    RegistrationResponse register(RegistrationRequest request);

    ActivationResponse activate(ActivationRequest request);

    LoginResponse login(LoginRequest request);

    ActionResponse requestResetPassword(ResetPasswordRequest request);

    ActionResponse confirmResetPassword(ConfirmResetPasswordRequest request);
}
