package com.khaled_amin.book_social_network.identity.user.account.api.controller;

import com.khaled_amin.book_social_network.core.api.ApiPageResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import com.khaled_amin.book_social_network.core.pagination.PageMapper;
import com.khaled_amin.book_social_network.core.pagination.PageResult;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.provider.ActorProvider;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.*;
import com.khaled_amin.book_social_network.identity.user.account.api.mapper.AccountAdminMapper;
import com.khaled_amin.book_social_network.identity.user.account.api.mapper.AccountMapper;
import com.khaled_amin.book_social_network.identity.user.account.application.service.AccountService;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("accounts")
@RequiredArgsConstructor
@Tag(name = "Account Management")
public class AccountController {
    private final AccountService accountService;
    private final AccountMapper accountMapper;
    private final AccountAdminMapper accountAdminMapper;
    private final ActorProvider actorProvider;


    @PreAuthorize("hasAuthority('account_create')")
    @PostMapping
    public ResponseEntity<ApiResponse<AccountAdminResponse>> createAccount(@Valid @RequestBody AccountCreateRequest request) {

        Account newAccount = accountService.create(request);

        AccountAdminResponse response = accountAdminMapper.toResponse(newAccount);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseFactory.success(response));
    }

    @PreAuthorize("hasAuthority('account_update_self')")
    @PatchMapping("/me")
    public ResponseEntity<ApiResponse<AccountResponse>> updateMyAccount(
            @Valid @RequestBody AccountUpdateRequest request) {

        Actor authaticatedActor = actorProvider.getCurrent();
        ActorCode accountCode = authaticatedActor.getActorIdentity().getActorCode();

        Account updatedAccount = accountService.update(accountCode,request);

        AccountResponse response = accountMapper.toResponse(updatedAccount);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @PreAuthorize("hasAuthority('account_update')")
    @PatchMapping("/{accountCode}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> updateAccount(
            @PathVariable String accountCode,
            @Valid @RequestBody AccountUpdateRequest request) {

        Account updatedAccount = accountService.update(
                ActorCode.of(accountCode),
                request
        );

        AccountAdminResponse response = accountAdminMapper.toResponse(updatedAccount);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @PreAuthorize("hasAuthority('account_read_self')")
    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AccountResponse>> getMyAccount() {


        Account account = accountService.viewMyAccount();

        AccountResponse response = accountMapper.toResponse(account);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }


    @PreAuthorize("hasAuthority('account_read')")
    @GetMapping("/{accountCode}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> getAccount(@PathVariable String accountCode) {

        Account account = accountService.viewAccount(
                ActorCode.of(accountCode)
        );

        AccountAdminResponse response = accountAdminMapper.toResponse(account);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @GetMapping
    @PreAuthorize("hasAuthority('account_read')")
    public ResponseEntity<ApiPageResponse<AccountAdminResponse>> getAll(@Valid AccountPageRequest pageRequest) {

        PageResult<Account> accounts = accountService.listAccounts(pageRequest);

        PageResult<AccountAdminResponse> response = PageMapper.map(accounts, accountAdminMapper::toResponse);

        return ResponseEntity.ok(
                ApiResponseFactory.page(response)
        );
    }


    @PreAuthorize("hasAuthority('account_assign_role')")
    @PutMapping("/{accountCode}/roles/{roleName}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> assignRole(
            @PathVariable String accountCode,
            @PathVariable String roleName) {

        Account account = accountService.assignRole(
                ActorCode.of(accountCode),
                RoleName.of(roleName)
        );

        AccountAdminResponse response =  accountAdminMapper.toResponse(account);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @PreAuthorize("hasAuthority('account_remove_role')")
    @DeleteMapping("/{accountCode}/roles/{roleName}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> removeRole(
            @PathVariable String accountCode,
            @PathVariable String roleName) {

        Account account = accountService.removeRole(
                ActorCode.of(accountCode),
                RoleName.of(roleName)
        );

        AccountAdminResponse response = accountAdminMapper.toResponse(account);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }


//    @PreAuthorize("hasAuthority('account_replace_roles')")
//    @PutMapping("/{accountCode}/roles")
//    public ResponseEntity<ApiResponse<AccountAdminResponse>> replaceRoles(
//            @PathVariable String accountCode,
//            @RequestBody @Valid AccountReplaceRolesRequest request) {
//
//        ActorCode actorCode = ActorCode.of(accountCode);
//        List<RoleName> roleNames = RoleName.of(request.getRoleNames());
//
//        Account account = accountService.replaceRoles(actorCode, roleNames);
//
//        AccountAdminResponse response = accountAdminMapper.toResponse(account);
//        return ResponseEntity.ok(
//                ApiResponseFactory.success(response)
//        );
//    }
}
