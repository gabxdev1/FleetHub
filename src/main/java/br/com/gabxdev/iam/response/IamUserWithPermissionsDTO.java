package br.com.gabxdev.iam.response;

import br.com.gabxdev.iam.domain.enums.PermissionLevel;

import java.util.UUID;

public record IamUserWithPermissionsDTO(
        String firstName,

        String lastName,

        String email,

        UUID accountId,

        Long serviceCatalogId,

        PermissionLevel permissionLevel
) {
}
