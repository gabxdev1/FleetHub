package br.com.gabxdev.iam.request;

import br.com.gabxdev.iam.domain.enums.PermissionLevel;

public record ServiceCatalogPermPostRequest(
        Long serviceCatalogId,

        PermissionLevel permissionLevel
) {
}
