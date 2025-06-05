package br.com.gabxdev.iam.response;

import java.util.UUID;

public record IamUserResponse(
        Long iamUserId,

        String email,

        String firstName,

        String lastName,

        UUID accountId
) {
}
