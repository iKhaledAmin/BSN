    package com.khaled_amin.book_social_network.auth.account.api.dto;

    import com.fasterxml.jackson.annotation.JsonProperty;
    import com.khaled_amin.book_social_network.security.jwt.JwtResponse;
    import lombok.*;
    import lombok.experimental.SuperBuilder;

    import java.util.List;

    @Getter
    @SuperBuilder
    public class AccountLoginResponse {


        @JsonProperty("account_info")
        private AccountInfo account;

        @JsonProperty("token_info")
        private JwtResponse token;




        @Getter
        @SuperBuilder
        public static class AccountInfo {

            private String username;

            @JsonProperty("actor_code")
            private String actorCode;

            @JsonProperty("actor_type")
            private String actorType;

            private List<String> roles;
        }
    }