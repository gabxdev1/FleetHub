package br.com.gabxdev.authentication.response;

import lombok.Builder;

@Builder
public record TokenJwtResponse(
        String accessToken,
        String tokenType,
        Long expiresIn
) {
}
