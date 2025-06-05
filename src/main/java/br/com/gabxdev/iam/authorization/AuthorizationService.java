package br.com.gabxdev.iam.authorization;

import br.com.gabxdev.core.common.Role;
import br.com.gabxdev.core.common.UserIdentity;
import br.com.gabxdev.core.exception.AccessDeniedException;
import br.com.gabxdev.iam.domain.enums.PermissionLevel;
import br.com.gabxdev.iam.service.IamUserServiceCatalogPermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AuthorizationService {

    private final IamUserServiceCatalogPermissionService catalogPermissionService;

    protected void assertPermission(UserIdentity currentUser, Long serviceId, PermissionLevel requiredPermission) {
        if (isOwner(currentUser.getRole())) {
            return;
        }

        var hasPermission = catalogPermissionService
                .hasPermission(currentUser.getId(), serviceId, requiredPermission);

        if (!hasPermission) {
            throw new AccessDeniedException();
        }
    }

    private boolean isOwner(List<Role> roles) {
        return roles.stream().anyMatch(role -> role == Role.OWNER);
    }
}
