package com.chatus.messagerouter.utils.AuthorizationUtils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.chatus.messagerouter.utils.SecondsToInstantDeserializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AuthorizationToken {

    @JsonProperty("access_token")
    private String accessToken;

    @JsonProperty("expires_in")
    @JsonDeserialize(using = SecondsToInstantDeserializer.class)
    private Instant expiresInAccessToken;

    @JsonProperty("refresh_token")
    private String refreshToken;

    @JsonProperty("refresh_expires_in")
    @JsonDeserialize(using = SecondsToInstantDeserializer.class)
    private Instant expiresInRefreshToken;
}
