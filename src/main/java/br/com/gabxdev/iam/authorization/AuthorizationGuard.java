package br.com.gabxdev.iam.authorization;

import br.com.gabxdev.core.common.AuthUtil;
import br.com.gabxdev.core.properties.ServiceRegister;
import br.com.gabxdev.iam.domain.enums.PermissionLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthorizationGuard {

    private final AuthorizationService authorizationService;

    private final AuthUtil authUtil;

    public void checkReadPermission(ServiceRegister serviceRegister) {
        checkPermission(PermissionLevel.READ, serviceRegister);
    }

    public void checkWritePermission(ServiceRegister serviceRegister) {
        checkPermission(PermissionLevel.WRITE, serviceRegister);
    }

    private void checkPermission(PermissionLevel permission, ServiceRegister serviceRegister) {
        var currentUser = authUtil.getCurrentUser();

        authorizationService.assertPermission(
                currentUser,
                serviceRegister.getId(),
                permission);
    }
}
