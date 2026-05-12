package com.khaled_amin.book_social_network.auth.account.application.service;

import com.khaled_amin.book_social_network.auth.account.api.dto.*;
import com.khaled_amin.book_social_network.auth.account.api.mapper.AuthenticationMapper;
import com.khaled_amin.book_social_network.auth.account.application.port.in.AuthenticationService;
import com.khaled_amin.book_social_network.auth.account.application.port.out.AccountAuthenticationProvider;
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
public class AuthenticationServiceImpl implements AuthenticationService {


    private final VerificationService verificationService;
    private final AccountService accountService;
    private final EmailService emailService;
    private final RoleService roleService;
    private final JwtService jwtService;
    private final AuthenticationMapper authenticationMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationProperties authProperties;
    private final EmailProperties emailProperties;
    private final AccountAuthenticationProvider authenticationProvider;


    @Transactional
    @Override
    public void createBootstrapAdmin(
            String username,
            String rawPassword,
            String email
    ) {

        if (accountService.existsByRoleName(
                SystemRole.SUPER_ADMIN.getName().value())) {
            return;
        }

        Role superAdminRole = roleService.getByName(
                SystemRole.SUPER_ADMIN.getName().value()
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
    public RegistrationResponse register(RegistrationRequest request){

        List<Role> defaultRole = roleService.getDefaultRoles();
        String encodedPassword = encodePassword(request.getPassword());

        AccountCreateCommand command = authenticationMapper.toCommand(request, encodedPassword);

        Account newAccount = accountService.create(command, defaultRole);

        String activationCode = verificationService.generateToken(
                TokenType.ACCOUNT_ACTIVATION,
                newAccount.getActorIdentity()
        );

        sendActivationEmail(newAccount, activationCode);

        return authenticationMapper.toRegistrationResponse(newAccount);
    }

    @Override
    @Transactional
    public ActivationResponse activate(ActivationRequest request){

        VerificationResult result = verificationService.validateToken(
                request.getCode(),
                TokenType.ACCOUNT_ACTIVATION
        );

        ActorCode actorCode = result.target().getActorCode();

        Account activatedAccount = accountService.activate(actorCode);

        return authenticationMapper.toActivationResponse(activatedAccount);
    }

    @Override
    @Transactional
    public LoginResponse login(LoginRequest request) {

         AccountPrincipal principal = authenticationProvider.authenticate(
                request.getUsername(),
                request.getPassword()
        );

        String jwtToken = jwtService.generateToken(principal);

        return authenticationMapper.toLoginResponse(jwtToken,principal);
    }

    @Transactional
    @Override
    public ActionResponse requestResetPassword(ResetPasswordRequest request) {


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
    public ActionResponse confirmResetPassword(ConfirmResetPasswordRequest request) {

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
