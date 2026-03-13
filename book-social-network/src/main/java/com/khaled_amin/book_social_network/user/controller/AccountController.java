package com.khaled_amin.book_social_network.user.controller;

import com.khaled_amin.book_social_network.common.dto.ApiResponse;
import com.khaled_amin.book_social_network.common.factory.ApiResponseFactory;
import com.khaled_amin.book_social_network.security.CurrentUserService;
import com.khaled_amin.book_social_network.user.model.dto.AccountRequest;
import com.khaled_amin.book_social_network.user.model.dto.AccountResponse;
import com.khaled_amin.book_social_network.user.model.dto.RegistrationRequest;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import com.khaled_amin.book_social_network.user.model.mapper.AccountMapper;
import com.khaled_amin.book_social_network.user.service.AccountService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
@Tag(name = "Account Management")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final CurrentUserService currentUserService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AccountResponse>> register(
            @Valid @RequestBody RegistrationRequest request) {

        Account account = accountService.register(request);

        AccountResponse response = accountMapper.toResponse(account);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseFactory.success(response));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<AccountResponse>> updateCurrentAccount(
            @Valid @RequestBody AccountRequest request) {

        Long accountId = currentUserService.getCurrentAccountId();

        Account updatedAccount = accountService.update(accountId, request);

        AccountResponse response = accountMapper.toResponse(updatedAccount);

        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> updateAccountById(
            @PathVariable Long id,
            @Valid @RequestBody AccountRequest request) {

        Account account = accountService.update(id, request);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        accountMapper.toResponse(account)
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AccountResponse>> getCurrentAccount() {

        Account account = currentUserService.getCurrentAccount();

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        accountMapper.toResponse(account)
                )
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountResponse>> getAccountById(
            @PathVariable Long id) {

        Account account = accountService.getById(id);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        accountMapper.toResponse(account)
                )
        );
    }



}
