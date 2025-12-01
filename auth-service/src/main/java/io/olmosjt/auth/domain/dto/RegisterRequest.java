package io.olmosjt.auth.domain.dto;

public record RegisterRequest(
        String email,
        String password
) {
}
