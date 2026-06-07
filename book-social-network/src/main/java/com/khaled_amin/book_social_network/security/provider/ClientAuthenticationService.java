package com.khaled_amin.book_social_network.security.provider;

import com.khaled_amin.book_social_network.security.principal.clinet.ClientPrincipal;

public class ClientAuthenticationService implements CredentialAuthenticationService <ClientPrincipal>{
    @Override
    public ClientPrincipal authenticate(String subject, String credential) {
        // TODO implement client authentication logic
        return null;
    }
}
