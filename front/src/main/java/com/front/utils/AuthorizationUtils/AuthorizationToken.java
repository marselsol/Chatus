package com.front.utils.AuthorizationUtils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.front.utils.SecondsToInstantDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class AuthorizationToken {

    private String accessToken;

    @JsonProperty("expires_in")
    @JsonDeserialize(using = SecondsToInstantDeserializer.class)
    private Instant expiresInAccessToken;

    @JsonProperty("refresh_expires_in")
    @JsonDeserialize(using = SecondsToInstantDeserializer.class)
    private String refreshToken;

    private Instant expiresInRefreshToken;

}
