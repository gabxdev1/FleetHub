package br.com.gabxdev.user.response;

import lombok.Builder;

import java.util.UUID;

@Builder
public record UserResponse(
        Long userId,

        String email,

        String firstName,

        String lastName,

        UUID accountId
) {
}
