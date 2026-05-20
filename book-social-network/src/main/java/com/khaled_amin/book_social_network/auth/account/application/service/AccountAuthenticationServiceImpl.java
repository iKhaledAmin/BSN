package com.khaled_amin.book_social_network.auth.account.application.service;

import com.khaled_amin.book_social_network.auth.account.api.dto.*;
import com.khaled_amin.book_social_network.auth.account.api.mapper.AccountAuthenticationMapper;
import com.khaled_amin.book_social_network.auth.account.application.config.AuthenticationProperties;
import com.khaled_amin.book_social_network.core.api.ActionResponse;
import com.khaled_amin.book_social_network.email.application.port.in.EmailService;
import com.khaled_amin.book_social_network.email.domain.command.EmailCreateCommand;
import com.khaled_amin.book_social_network.email.domain.model.EmailTemplate;
import com.khaled_amin.book_social_network.email.infrastructure.config.EmailProperties;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.EncodedPassword;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.verification.application.dto.VerificationResult;
import com.khaled_amin.book_social_network.identity.verification.application.service.VerificationService;
import com.khaled_amin.book_social_network.identity.verification.domain.model.TokenType;
import com.khaled_amin.book_social_network.identity.user.role.application.service.RoleService;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.account.application.service.AccountService;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.security.principal.account.AccountPrincipal;
import com.khaled_amin.book_social_network.security.jwt.JwtService;
import com.khaled_amin.book_social_network.security.provider.AccountCredentialAuthenticationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class AccountAuthenticationServiceImpl implements AccountAuthenticationService {


    private final VerificationService verificationService;
    private final AccountService accountService;
    private final EmailService emailService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final AccountAuthenticationMapper accountAuthenticationMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProperties authProperties;
    private final EmailProperties emailProperties;
    private final AccountCredentialAuthenticationService authenticationService;



    @Transactional
    @Override
    public void createBootstrapAdmin(String username, String rawPassword, String email) {

        if (accountService.existsByRoleName(
                SystemRole.SUPER_ADMIN.getName().value())) {
            return;
        }

        Role superAdminRole = roleService.getByName(
                SystemRole.SUPER_ADMIN.getName()
        );

        String encodedPassword = passwordEncoder.encode(rawPassword);

        AccountCreateCommand command = AccountCreateCommand.of(
                username,
                encodedPassword,
                email,
                "System",
                "Administrator"
        );

        Account account = accountService.create(
                command,
                List.of(superAdminRole)
        );

        accountService.activate(account.getAccountCode());
    }

    @Override
    @Transactional
    public AccountRegistrationResponse register(AccountRegistrationRequest request){

        List<Role> defaultRole = roleService.getDefaultRoles();
        String encodedPassword = encodePassword(request.getPassword());

        AccountCreateCommand command = accountAuthenticationMapper.toCommand(request, encodedPassword);

        Account newAccount = accountService.create(command, defaultRole);

        String activationCode = verificationService.generateToken(
                TokenType.ACCOUNT_ACTIVATION,
                newAccount.getActorIdentity()
        );

        sendActivationEmail(newAccount, activationCode);

        return accountAuthenticationMapper.toRegistrationResponse(newAccount);
    }

    @Override
    @Transactional
    public AccountActivationResponse activate(AccountActivationRequest request){

        VerificationResult result = verificationService.validateToken(
                request.getCode(),
                TokenType.ACCOUNT_ACTIVATION
        );

        ActorCode actorCode = result.target().getActorCode();

        Account activatedAccount = accountService.activate(actorCode);

        return accountAuthenticationMapper.toActivationResponse(activatedAccount);
    }


    @Override
    @Transactional
    public AccountLoginResponse login(AccountLoginRequest request) {

        AccountPrincipal principal = authenticationService.authenticate(
                request.getUsername(),
                request.getPassword()
        );

        String jwtToken = jwtService.generateToken(principal);

        return accountAuthenticationMapper.toLoginResponse(jwtToken,principal);
    }



    @Transactional
    @Override
    public ActionResponse requestResetPassword(AccountResetPasswordRequest request) {


        Optional<Account> optionalAccount = accountService.getOptionalByEmail(request.getEmailAddress());

        if (optionalAccount.isEmpty()) {
            // TODO: log
            return ActionResponse.builder()
                    .message("If an account exists for this email address,you will receive a reset password email.")
                    .build();

        }

        Account account = optionalAccount.get();

        String resetCode = verificationService.generateToken(
                TokenType.RESET_PASSWORD,
                account.getActorIdentity()
        );

        sendResetPasswordEmail(account, resetCode);

        return ActionResponse.builder()
                .message("If an account exists for this email address,you will receive a reset password email.")
                .build();
    }

    @Override
    @Transactional
    public ActionResponse confirmResetPassword(AccountConfirmResetPasswordRequest request) {

        // Validate token
        VerificationResult result = verificationService.validateToken(
                request.getCode(),
                TokenType.RESET_PASSWORD
        );

        ActorCode actorCode = result.target().getActorCode();

        // Encode password
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // Apply domain operation
        accountService.resetPassword(
                actorCode,
                EncodedPassword.of(encodedPassword)
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
        } catch (Exception e) {
            // TODO: loge exception not throw it
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
        } catch (Exception e) {
            // TODO: loge exception not throw it
        }

    }


    private String encodePassword(String raw) {
        return passwordEncoder.encode(raw);
    }

    // -------------------------------------- End Helper methods ----------------------------------- //
}
