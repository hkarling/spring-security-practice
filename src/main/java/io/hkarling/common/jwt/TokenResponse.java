package io.hkarling.common.jwt;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TokenResponse {

    private String grantType;
    private String accessToken;
    private String refreshToken;
    private Long accessTokenExpiry;
    @JsonIgnore
    private Long refreshTokenExpiry;
}
