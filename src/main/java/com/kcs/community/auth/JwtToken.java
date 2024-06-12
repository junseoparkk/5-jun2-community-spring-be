package com.kcs.community.auth;

import lombok.Builder;

@Builder
public record JwtToken(String grantType, String accessToken, String refreshToken) {
}
