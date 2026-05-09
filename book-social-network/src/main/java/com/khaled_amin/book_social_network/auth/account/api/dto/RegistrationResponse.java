package com.khaled_amin.book_social_network.auth.account.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationResponse {
    private String status;
    private String email;
}
