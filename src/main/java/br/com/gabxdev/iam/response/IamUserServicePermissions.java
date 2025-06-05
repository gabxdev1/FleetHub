package br.com.gabxdev.iam.response;

import br.com.gabxdev.iam.domain.enums.PermissionLevel;
import lombok.Builder;

@Builder
public record IamUserServicePermissions(
        Long serviceCatalogId,

        PermissionLevel permissionLevel
) {
}
