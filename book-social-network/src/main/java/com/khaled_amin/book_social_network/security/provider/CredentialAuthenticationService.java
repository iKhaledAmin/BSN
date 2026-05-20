package com.khaled_amin.book_social_network.security.provider;

import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;

public interface CredentialAuthenticationService <P extends AuthenticatedPrincipal> {

    P authenticate(String subject, String credential);
}