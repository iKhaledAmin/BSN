    package com.khaled_amin.book_social_network.identity.user.account.api.dto;

    import com.fasterxml.jackson.annotation.JsonProperty;
    import lombok.Getter;
    import lombok.Setter;
    import lombok.experimental.SuperBuilder;

    import java.util.List;

    @Getter
    @Setter
    @SuperBuilder
    public class AccountResponse extends AccountBaseResponse{
        @JsonProperty("roles")
        private List<String> roles;
    }
