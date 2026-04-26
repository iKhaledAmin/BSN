package com.khaled_amin.book_social_network.auth;

import com.khaled_amin.book_social_network.email.EmailService;
import com.khaled_amin.book_social_network.email.EmailTemplate;
import com.khaled_amin.book_social_network.role.application.service.RoleService;
import com.khaled_amin.book_social_network.user.application.service.AccountService;
import com.khaled_amin.book_social_network.user.domain.command.AccountCreateCommand;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final AccountService accountService;
    private final TokenRepository tokenRepository;
    private final EmailService emailService;

    @Value("${application.mailing.frontend.activation-url}")
    private final String activationUrl;

    public void register(RegistrationRequest request) throws MessagingException {

        List<Long> defaultRoleIds = roleService.getAllDefaultRoleIds();
        String encodedPassword = encodePassword(request.getPassword());


        AccountCreateCommand command = AccountCreateCommand.of(
                request.getUsername(),
                encodedPassword,
                request.getEmailAddress(),
                request.getFirstName(),
                request.getLastName()
        );

        Account newAccount = accountService.create(command, defaultRoleIds);

        sendVerificationEmail(newAccount);

    }

    private void sendVerificationEmail(Account account) throws MessagingException {

        var activationToken = generateAndSaveActivationToken(account);

        // send email
        emailService.sendEmail(
                account.getEmailAddress(),
                account.getProfile().getFullName(),
                EmailTemplate.ACTIVATION_ACCOUNT,
                activationUrl,
                activationToken,
                "Account Activation"
        );


    }

    private String generateAndSaveActivationToken(Account account) {
        String generatedCode = generateActivationCode(6);

        Token token = Token.builder()
                .token(generatedCode)
                .createdAt(LocalDateTime.now())
                .expiresAt(LocalDateTime.now().plusMinutes(15))
                .account(account)
                .build();

        tokenRepository.save(token);

        return generatedCode;
    }

    private String generateActivationCode(int length) {
        String characters = "0123456789";
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            codeBuilder.append(characters.charAt(randomIndex));
        }
        return codeBuilder.toString();
    }


    private String encodePassword(String raw) {
        return passwordEncoder.encode(raw);
    }

}
