package com.khaled_amin.book_social_network.user.controller;

import com.khaled_amin.book_social_network.common.dto.ApiResponse;
import com.khaled_amin.book_social_network.common.factory.ApiResponseFactory;
import com.khaled_amin.book_social_network.security.CurrentUserService;
import com.khaled_amin.book_social_network.user.model.dto.*;
import com.khaled_amin.book_social_network.user.model.dto.AccountAdminResponse;
import com.khaled_amin.book_social_network.user.model.dto.AccountNormalResponse;
import com.khaled_amin.book_social_network.user.model.entity.Account;
import com.khaled_amin.book_social_network.user.model.mapper.AccountAdminMapper;
import com.khaled_amin.book_social_network.user.model.mapper.AccountNormalMapper;
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
    private final AccountNormalMapper accountNormalMapper;
    private final AccountAdminMapper accountAdminMapper;
    private final CurrentUserService currentUserService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AccountNormalResponse>> register(
            @Valid @RequestBody RegistrationRequest request) {

        Account account = accountService.register(request);

        AccountNormalResponse response = accountNormalMapper.toResponse(account);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseFactory.success(response));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<AccountNormalResponse>> updateCurrentAccount(
            @Valid @RequestBody AccountUpdateRequest request) {

        Long accountId = currentUserService.getCurrentAccountId();

        Account updatedAccount = accountService.update(accountId, request);

        AccountNormalResponse response = accountNormalMapper.toResponse(updatedAccount);

        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountNormalResponse>> updateAccountById(
            @PathVariable Long id,
            @Valid @RequestBody AccountUpdateRequest request) {

        Account account = accountService.update(id, request);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        accountNormalMapper.toResponse(account)
                )
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AccountNormalResponse>> getCurrentAccount() {

        Account account = currentUserService.getCurrentAccount();

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        accountNormalMapper.toResponse(account)
                )
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> getAccountById(
            @PathVariable Long id) {

        Account account = accountService.getById(id);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        accountAdminMapper.toResponse(account)
                )
        );
    }


    @PostMapping("/{accountId}/roles/{roleId}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> assignRole(
            @PathVariable Long accountId,
            @PathVariable Long roleId) {

        Account account = accountService.assignRole(accountId, roleId);

        return ResponseEntity.ok(
                ApiResponseFactory.success(accountAdminMapper.toResponse(account))
        );
    }

    @DeleteMapping("/{accountId}/roles/{roleId}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> removeRole(
            @PathVariable Long accountId,
            @PathVariable Long roleId) {

        Account account = accountService.removeRole(accountId, roleId);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        accountAdminMapper.toResponse(account)
                )
        );
    }


    @PutMapping("/{accountId}/roles")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> setRoles(
            @PathVariable Long accountId,
            @RequestBody @Valid SetAccountRolesRequest request) {

        Account account = accountService.setRoles(accountId, request.getRoleIds());

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        accountAdminMapper.toResponse(account)
                )
        );
    }
}
