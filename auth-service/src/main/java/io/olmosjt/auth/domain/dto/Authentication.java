package io.olmosjt.auth.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.olmosjt.common.enums.UserRole;

import java.util.UUID;

public final class Authentication {
    private Authentication() {
    }

    public record Request(String email, String password) {
    }

    public record Response(
            @JsonProperty("access_token") String accessToken,
            @JsonProperty("refresh_token") String refreshToken,
            @JsonProperty("user") UserInfo user
    ) {
    }

    public record UserInfo(
            UUID id,
            String email,
            String firstName,
            String lastName,
            @JsonProperty("avatar_url") String avatarUrl,
            UserRole role
    ) {}
}
