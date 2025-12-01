package io.olmosjt.common.dto.response;

import lombok.Builder;

import java.time.Instant;
import java.util.List;

@Builder
public record ErrorResponse(
        String path,
        String error,
        String message,
        int status,
        Instant timestamp,
        List<ValidationError> validationErrors
) {
    public record ValidationError(String field, String message) {}
}
