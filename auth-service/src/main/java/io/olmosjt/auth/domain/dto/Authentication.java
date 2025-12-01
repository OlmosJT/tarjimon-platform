package io.olmosjt.auth.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.olmosjt.auth.domain.UserRoleType;

public final class Authentication {
    private Authentication() {
    }

    public record Request(String email, String password) {
    }

    public record Response(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("role") UserRoleType role
    ) {
    }
}
