package com.khaled_amin.book_social_network.security.provider;

import com.khaled_amin.book_social_network.auth.account.application.port.out.AccountAuthenticationProvider;
import com.khaled_amin.book_social_network.security.principal.account.AccountPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.khaled_amin.book_social_network.security.exception.SecurityException;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SpringSecurityAccountAuthenticationProvider
        implements AccountAuthenticationProvider {

    private final AuthenticationManager authenticationManager;

    @Override
    public AccountPrincipal authenticate(String username, String password) {

        try {

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(
                                    username,
                                    password
                            )
                    );

            return (AccountPrincipal) authentication.getPrincipal();

        } catch (BadCredentialsException ex) {

            throw SecurityException.invalidCredentials();

        } catch (DisabledException ex) {

            throw SecurityException.principalDisabled();

        } catch (LockedException ex) {

            throw SecurityException.principalLocked();

        }
    }
}