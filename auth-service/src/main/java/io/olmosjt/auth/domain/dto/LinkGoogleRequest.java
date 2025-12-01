package io.olmosjt.auth.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LinkGoogleRequest(
        @JsonProperty("id_token") String idToken
) {
}
