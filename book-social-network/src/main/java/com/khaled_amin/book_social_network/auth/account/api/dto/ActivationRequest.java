package com.khaled_amin.book_social_network.auth.account.api.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActivationRequest {
    @NotBlank(message = "Activation code must not be blank")
    private String code;
}
