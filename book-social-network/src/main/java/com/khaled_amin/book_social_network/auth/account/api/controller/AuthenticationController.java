package com.khaled_amin.book_social_network.auth.account.api.controller;

import com.khaled_amin.book_social_network.auth.account.api.dto.*;
import com.khaled_amin.book_social_network.auth.account.application.port.in.AuthenticationService;
import com.khaled_amin.book_social_network.core.api.ActionResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponse;
import com.khaled_amin.book_social_network.core.api.ApiResponseFactory;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/account")
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class AuthenticationController {
    private final AuthenticationService authService;


    @PostMapping("/register")
    public ResponseEntity<ApiResponse<RegistrationResponse>> register(@RequestBody @Valid RegistrationRequest request){

        RegistrationResponse response = authService.register(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseFactory.success(response));
    }


    @PostMapping("/activate")
    public ResponseEntity<ApiResponse<ActivationResponse>> activate(@RequestBody @Valid ActivationRequest request) {

        ActivationResponse response = authService.activate(request);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@RequestBody @Valid LoginRequest request) {

        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @PostMapping("/reset-password-request")
    public ResponseEntity<ApiResponse<ActionResponse>> requestResetPassword(@RequestBody @Valid ResetPasswordRequest request) {

        ActionResponse response = authService.requestResetPassword(request);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }

    @PostMapping("/reset-password-confirm")
    public ResponseEntity<ApiResponse<ActionResponse>> confirmResetPassword(
            @RequestBody @Valid ConfirmResetPasswordRequest request
    ) {
        ActionResponse response = authService.confirmResetPassword(request);
        return ResponseEntity.ok(
                ApiResponseFactory.success(response)
        );
    }




}
