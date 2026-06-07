package com.khaled_amin.book_social_network.core.logging.audit;


import com.khaled_amin.book_social_network.core.exception.security.SecurityException;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;

public interface SecurityEventLogger {

    void authenticationSucceeded(AuthenticatedPrincipal principal);
    void authenticationFailed(SecurityException ex);

    void authorizationDenied(String method, String path , String message);

    void loginSucceeded(AuthenticatedPrincipal principal);

    void loginFailed(String username, SecurityException ex);




}