package com.khaled_amin.book_social_network.user.api.controller;

import com.khaled_amin.book_social_network.core.api.ApiResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import com.khaled_amin.book_social_network.role.domain.value.RoleId;
import com.khaled_amin.book_social_network.user.api.dto.AccountAdminResponse;
import com.khaled_amin.book_social_network.user.api.dto.AccountNormalResponse;
import com.khaled_amin.book_social_network.user.api.dto.AccountReplaceRolesRequest;
import com.khaled_amin.book_social_network.user.api.dto.AccountUpdateRequest;
import com.khaled_amin.book_social_network.user.api.mapper.AccountAdminMapper;
import com.khaled_amin.book_social_network.user.api.mapper.AccountNormalMapper;
import com.khaled_amin.book_social_network.user.application.actor.AccountActorResolver;
import com.khaled_amin.book_social_network.user.application.service.AccountService;
import com.khaled_amin.book_social_network.user.domain.command.AccountUpdateCommand;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import com.khaled_amin.book_social_network.user.domain.value.AccountId;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    private final AccountActorResolver accountActorResolver;


    @PutMapping("/me")
    public ResponseEntity<ApiResponse<AccountNormalResponse>> updateCurrentAccount(
            @Valid @RequestBody AccountUpdateRequest request) {

        Long accountId = accountActorResolver.getCurrentAccountId();
        AccountUpdateCommand command = accountNormalMapper.toCommand(request);

        Account updatedAccount = accountService.update(AccountId.of(accountId) , command);

        AccountNormalResponse response = accountNormalMapper.toResponse(updatedAccount);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountNormalResponse>> updateAccountById(
            @PathVariable Long id,
            @Valid @RequestBody AccountUpdateRequest request) {

        AccountId accountId = AccountId.of(id);
        AccountUpdateCommand command = accountNormalMapper.toCommand(request);

        Account updatedAccount = accountService.update(accountId, command);


        AccountNormalResponse response = accountNormalMapper.toResponse(updatedAccount);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AccountNormalResponse>> getCurrentAccount() {

        Long accountId = accountActorResolver.getCurrentAccountId();
        Account account = accountService.getById(accountId);

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        accountNormalMapper.toResponse(account)
                )
        );
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> getAccountById(
            @PathVariable Long id) {

        Account account = accountService.getById(AccountId.of(id));

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

        Account account = accountService.assignRoles(AccountId.of(accountId), RoleId.of(roleId));

        return ResponseEntity.ok(
                ApiResponseFactory.success(accountAdminMapper.toResponse(account))
        );
    }

    @DeleteMapping("/{accountId}/roles/{roleId}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> removeRole(
            @PathVariable Long accountId,
            @PathVariable Long roleId) {

        Account account = accountService.removeRole(AccountId.of(accountId), RoleId.of(roleId));

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        accountAdminMapper.toResponse(account)
                )
        );
    }


    @PutMapping("/{accountId}/roles")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> replaceRoles(
            @PathVariable Long accountId,
            @RequestBody @Valid AccountReplaceRolesRequest request) {

        Account account = accountService.replaceRoles(AccountId.of(accountId),request.getRoleIds());

        return ResponseEntity.ok(
                ApiResponseFactory.success(
                        accountAdminMapper.toResponse(account)
                )
        );
    }
}
