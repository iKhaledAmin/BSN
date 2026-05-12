package com.khaled_amin.book_social_network.identity.user.account.api.controller;

import com.khaled_amin.book_social_network.core.api.ApiResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.provider.ActorProvider;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountAdminResponse;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountNormalResponse;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountReplaceRolesRequest;
import com.khaled_amin.book_social_network.identity.user.account.api.dto.AccountUpdateRequest;
import com.khaled_amin.book_social_network.identity.user.account.api.mapper.AccountAdminMapper;
import com.khaled_amin.book_social_network.identity.user.account.api.mapper.AccountNormalMapper;
import com.khaled_amin.book_social_network.identity.user.account.application.service.AccountService;
import com.khaled_amin.book_social_network.identity.user.account.domain.command.AccountUpdateCommand;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import com.khaled_amin.book_social_network.identity.user.account.domain.value.AccountId;
import com.khaled_amin.book_social_network.identity.user.role.domain.value.RoleName;
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
    private final ActorProvider actorProvider;


    @PutMapping("/me")
    public ResponseEntity<ApiResponse<AccountNormalResponse>> updateCurrentAccount(
            @Valid @RequestBody AccountUpdateRequest request) {

        Actor authaticatedActor = actorProvider.getCurrent();
        ActorCode accountCode = authaticatedActor.getActorIdentity().getActorCode();

        AccountUpdateCommand command = accountNormalMapper.toCommand(request);

        Account updatedAccount = accountService.update(accountCode,command);

        AccountNormalResponse response = accountNormalMapper.toResponse(updatedAccount);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @PutMapping("/{accountCode}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> updateAccount(
            @PathVariable String accountCode,
            @Valid @RequestBody AccountUpdateRequest request) {

        AccountUpdateCommand command = accountNormalMapper.toCommand(request);
        Account updatedAccount = accountService.update(
                ActorCode.of(accountCode),
                command
        );

        AccountAdminResponse response = accountAdminMapper.toResponse(updatedAccount);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<AccountNormalResponse>> getCurrentAccount() {

        Actor authntcatedActor = actorProvider.getCurrent();
        Account account = accountService.getByIdentity(authntcatedActor.getActorIdentity());

        AccountNormalResponse response = accountNormalMapper.toResponse(account);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }


    @GetMapping("/{accountCode}")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> getAccount(
            @PathVariable String accountCode) {

        Account account = accountService.getByAccountCode(
                ActorCode.of(accountCode)
        );

        AccountAdminResponse response = accountAdminMapper.toResponse(account);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @PostMapping("/{accountCode}/roles/{roleName}")
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


    @PutMapping("/{accountCode}/roles")
    public ResponseEntity<ApiResponse<AccountAdminResponse>> replaceRoles(
            @PathVariable String accountCode,
            @RequestBody @Valid AccountReplaceRolesRequest request) {

        Account account = accountService.replaceRoles(
                ActorCode.of(accountCode),
                request.getRoleNames()
        );

        AccountAdminResponse response = accountAdminMapper.toResponse(account);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }
}
