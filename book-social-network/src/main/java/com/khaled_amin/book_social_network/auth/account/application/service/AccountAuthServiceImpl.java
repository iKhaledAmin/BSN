package com.khaled_amin.book_social_network.auth.account.application.service;

import com.khaled_amin.book_social_network.auth.account.api.dto.*;
import com.khaled_amin.book_social_network.core.exception.security.SecurityException;
import com.khaled_amin.book_social_network.auth.account.api.mapper.AccountAuthMapper;
import com.khaled_amin.book_social_network.auth.account.application.config.AuthenticationProperties;
import com.khaled_amin.book_social_network.core.api.ActionResponse;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;
import com.khaled_amin.book_social_network.core.logging.audit.BusinessEventLogger;
import com.khaled_amin.book_social_network.core.logging.audit.SecurityEventLogger;
import com.khaled_amin.book_social_network.core.logging.core.ActorLoggingContext;
import com.khaled_amin.book_social_network.email.application.port.in.EmailService;
import com.khaled_amin.book_social_network.email.domain.command.EmailCreateCommand;
import com.khaled_amin.book_social_network.email.domain.model.EmailTemplate;
import com.khaled_amin.book_social_network.email.infrastructure.config.EmailProperties;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountCreateRequest;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.RawPassword;
import com.khaled_amin.book_social_network.identity.verification.application.dto.VerificationResult;
import com.khaled_amin.book_social_network.identity.verification.application.service.VerificationService;
import com.khaled_amin.book_social_network.identity.verification.domain.model.TokenType;
import com.khaled_amin.book_social_network.identity.user.role.application.service.RoleService;
import com.khaled_amin.book_social_network.identity.user.account.application.service.AccountService;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.security.principal.account.AccountPrincipal;
import com.khaled_amin.book_social_network.security.jwt.JwtService;
import com.khaled_amin.book_social_network.security.provider.AccountAuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;


import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AccountAuthServiceImpl implements AccountAuthService {


    private final VerificationService verificationService;
    private final AccountService accountService;
    private final EmailService emailService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final AccountAuthMapper accountAuthMapper;
    private final AuthenticationProperties authProperties;
    private final EmailProperties emailProperties;
    private final AccountAuthenticationService authenticationService;
    private final SecurityEventLogger securityEventLogger;
    private final BusinessEventLogger businessEventLogger;



    @Override
    @Transactional
    public AccountRegistrationResponse register(AccountRegistrationRequest request){

        List<String> defaultRoleNames = roleService.getDefaultRoleNames();

        AccountCreateRequest createRequest = accountAuthMapper.toCreateRequest(request,defaultRoleNames);

        Account newAccount = accountService.create(createRequest);

        String activationCode = verificationService.generateToken(
                TokenType.ACCOUNT_ACTIVATION,
                newAccount.getActorIdentity()
        );

        sendActivationEmail(newAccount, activationCode);

        businessEventLogger.accountRegistered(newAccount.getActorCode().toString());

        return accountAuthMapper.toRegistrationResponse(newAccount);
    }

    @Override
    @Transactional
    public AccountActivationResponse activate(AccountActivationRequest request){

        VerificationResult result = verificationService.verifyToken(
                request.getCode(),
                TokenType.ACCOUNT_ACTIVATION
        );

        ActorCode actorCode = result.target().getActorCode();

        Account activatedAccount = accountService.activate(actorCode);

        return accountAuthMapper.toActivationResponse(activatedAccount);
    }


    @Override
    @Transactional
    public AccountLoginResponse login(AccountLoginRequest request) {

        try {

            AccountPrincipal principal = authenticationService.authenticate(
                    request.getUsername(),
                    request.getPassword()
            );

            accountService.login(principal.getActorCode());

            String jwtToken = jwtService.generateToken(principal);

            // Set actor context
            ActorLoggingContext.put(principal);

            // Log login success
            securityEventLogger.loginSucceeded(principal);

            return accountAuthMapper.toLoginResponse(jwtToken, principal);

        } catch (SecurityException ex) {

            securityEventLogger.loginFailed(
                    request.getUsername(),
                    ex
            );

            throw ex;
        }
    }

    @Transactional
    @Override
    public ActionResponse requestResetPassword(AccountResetPasswordRequest request) {


        Optional<Account> optionalAccount = accountService.getOptionalByEmail(request.getEmailAddress());

        if (optionalAccount.isEmpty()) {
            return ActionResponse.builder()
                    .message("If an account exists for this emailAddress address,you will receive a reset password emailAddress.")
                    .build();

        }

        Account account = optionalAccount.get();

        String resetCode = verificationService.generateToken(
                TokenType.ACCOUNT_RESET_PASSWORD,
                account.getActorIdentity()
        );

        sendResetPasswordEmail(account, resetCode);

        businessEventLogger.passwordResetRequested(
                account.getAccountCode().toString()
        );

        return ActionResponse.builder()
                .message("If an account exists for this emailAddress address,you will receive a reset password emailAddress.")
                .build();
    }

    @Override
    @Transactional
    public ActionResponse resetPassword(AccountConfirmResetPasswordRequest request) {

        // Validate token
        VerificationResult result = verificationService.verifyToken(
                request.getCode(),
                TokenType.ACCOUNT_RESET_PASSWORD
        );

        ActorCode actorCode = result.target().getActorCode();

        // Apply domain operation
        accountService.resetPassword(
                actorCode,
                RawPassword.of(request.getPassword())
        );

        // Response
        return ActionResponse.builder()
                .message("Your password has been reset successfully.")
                .build();
    }


    // -------------------------------------- Helper methods --------------------------------------- //
    private void sendActivationEmail(Account account, String activationCode) {

        String activationUrl = authProperties.activation().frontendUrl();
        String sender = emailProperties.sender().from();
        String replyTo = emailProperties.sender().replyTo();

        Map<String, Object> variables = Map.of(
                "username", account.getUsername(),
                "activationCode", activationCode,
                "activationUrl", activationUrl
        );

        EmailCreateCommand command = EmailCreateCommand.of(
                sender,
                account.getEmailAddress(),
                replyTo,
                Set.of(),
                Set.of(),
                EmailTemplate.ACCOUNT_ACTIVATION.getSubject(),
                EmailTemplate.ACCOUNT_ACTIVATION.getName()
        );

        try {
            emailService.sendEmail(command, variables);
        } catch (TechnicalException e) {
            // Intentionally ignored.
            // Email delivery failure is non-blocking.
            // The Email module already logs the failure and schedules retries.
        }

    }

    private void sendResetPasswordEmail(Account account, String resetCode) {
        String sender = emailProperties.sender().from();
        String replyTo = emailProperties.sender().replyTo();

        Map<String, Object> variables = Map.of(
                "username", account.getUsername(),
                "resetCode", resetCode
        );

        EmailCreateCommand command = EmailCreateCommand.of(
                sender,
                account.getEmailAddress(),
                replyTo,
                Set.of(),
                Set.of(),
                EmailTemplate.RESET_PASSWORD.getSubject(),
                EmailTemplate.RESET_PASSWORD.getName()
        );

        try {
            emailService.sendEmail(command, variables);
        } catch (TechnicalException e) {
            // Intentionally ignored.
            // Email delivery failure is non-blocking.
            // The Email module already logs the failure and schedules retries.
        }

    }

    // -------------------------------------- End Helper methods ----------------------------------- //
}
