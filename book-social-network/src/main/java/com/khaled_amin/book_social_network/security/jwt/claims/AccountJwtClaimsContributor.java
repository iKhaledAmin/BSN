package com.khaled_amin.book_social_network.security.jwt.claims;

import com.khaled_amin.book_social_network.security.principal.account.AccountPrincipal;
import io.jsonwebtoken.JwtBuilder;
import org.springframework.stereotype.Component;

@Component
public class AccountJwtClaimsContributor implements JwtClaimsContributor<AccountPrincipal> {

    public static final String CLAIM_ROLES = "roles";
    public static final String CLAIM_PERMISSIONS = "permissions";

    @Override
    public Class<AccountPrincipal> getSupportedPrincipal() {
        return AccountPrincipal.class;
    }

    @Override
    public void contribute(JwtBuilder builder, AccountPrincipal principal) {

        builder.claim(CLAIM_ROLES, principal.getRoles());
        builder.claim(CLAIM_PERMISSIONS, principal.getPermissions());
    }
}