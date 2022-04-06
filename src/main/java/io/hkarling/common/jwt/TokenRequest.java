package io.hkarling.common.jwt;

import lombok.Getter;

@Getter
public class TokenRequest {

    private String accessToken;
    private String refreshToken;
}
