package br.com.gabxdev.iam.response;

import lombok.Builder;

import java.util.List;

@Builder
public record IamUserGetResponse(
        String firstName,

        String lastName,

        String email,

        List<IamUserServicePermissions> services
) {
}
