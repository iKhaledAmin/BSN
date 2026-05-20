package com.khaled_amin.book_social_network.security.jwt.claims;

import com.khaled_amin.book_social_network.security.principal.clinet.ClientPrincipal;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.stereotype.Component;

@Component
public class ClientJwtClaimsContributor implements JwtClaimsContributor<ClientPrincipal> {

    public static final String CLAIM_SCOPE = "scope";

    @Override
    public Class<ClientPrincipal> getSupportedPrincipal() {
        return ClientPrincipal.class;
    }

    @Override
    public void contribute(JwtBuilder builder,
                           ClientPrincipal principal) {

        String scope = String.join(" ", principal.getScopes());

        builder.claim(CLAIM_SCOPE, scope);
    }
}