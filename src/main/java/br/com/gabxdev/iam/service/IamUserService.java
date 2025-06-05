package br.com.gabxdev.iam.service;

import br.com.gabxdev.core.common.AuthUtil;
import br.com.gabxdev.core.exception.AccessDeniedException;
import br.com.gabxdev.core.exception.EmailAlreadyExistsException;
import br.com.gabxdev.core.exception.NotFoundException;
import br.com.gabxdev.core.properties.FleetHubProperties;
import br.com.gabxdev.core.properties.ServiceRegister;
import br.com.gabxdev.iam.authorization.AuthorizationGuard;
import br.com.gabxdev.iam.domain.IamUser;
import br.com.gabxdev.iam.repository.IamUserRepository;
import br.com.gabxdev.iam.request.ServiceCatalogPermPostRequest;
import br.com.gabxdev.iam.response.IamUserGetResponse;
import br.com.gabxdev.iam.response.IamUserServicePermissions;
import br.com.gabxdev.iam.response.IamUserWithPermissionsDTO;
import br.com.gabxdev.user.projection.LoginResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IamUserService {
    private final AuthorizationGuard authorizationGuard;

    private final IamUserRepository iamUserRepository;

    private final FleetHubProperties fleetHubProperties;

    private final IamUserServiceCatalogPermissionService iamUserServiceCatalogPermissionService;

    private final AuthUtil authUtil;

    private final PasswordEncoder passwordEncoder;

    public LoginResponse findAccountUserIdByEmailOrThrow(String email, UUID accountId) {
        return iamUserRepository.findIdByEmailIgnoreCase(email, accountId)
                .orElseThrow(() -> new NotFoundException("IamUser not found with email: " + email));
    }

    @Transactional
    public IamUser save(IamUser iamUser, List<ServiceCatalogPermPostRequest> serviceCatalogPermList) {
        authorizationGuard.checkReadPermission(getServiceRegister());

        var currentUser = authUtil.getCurrentUser();

        iamUser.setPassword(passwordEncoder.encode(iamUser.getPassword()));
        iamUser.setAccountId(currentUser.getAccountId());

        validateEmail(iamUser.getEmail(), currentUser.getAccountId());

        var iamUserSaved = iamUserRepository.save(iamUser);

        iamUserServiceCatalogPermissionService.save(
                iamUserSaved,
                serviceCatalogPermList);

        return iamUserSaved;
    }

    public IamUserGetResponse findIamUserWithPermissions(Long iamUserId) {
        authorizationGuard.checkWritePermission(getServiceRegister());

        var currentUser = authUtil.getCurrentUser();

        var iamUserWithPermissions = iamUserRepository.findIamUserWithPermissions(iamUserId, currentUser.getAccountId());

        assertThatNotEmpty(iamUserWithPermissions);

        var serviceList = buildIamUserWithPermissions(iamUserWithPermissions);

        return buildIamUserGetResponse(iamUserWithPermissions.getFirst(), serviceList);
    }

    private List<IamUserServicePermissions> buildIamUserWithPermissions(List<IamUserWithPermissionsDTO> iamUserWithPermissions) {
        return iamUserWithPermissions
                .stream()
                .map(perm -> IamUserServicePermissions.builder()
                        .serviceCatalogId(perm.serviceCatalogId())
                        .permissionLevel(perm.permissionLevel())
                        .build())
                .toList();
    }

    private IamUserGetResponse buildIamUserGetResponse(IamUserWithPermissionsDTO iamUserWithPermissions, List<IamUserServicePermissions> serviceList) {
        return IamUserGetResponse.builder()
                .firstName(iamUserWithPermissions.firstName())
                .lastName(iamUserWithPermissions.lastName())
                .email(iamUserWithPermissions.email())
                .services(serviceList)
                .build();
    }

    private void assertThatNotEmpty(List<IamUserWithPermissionsDTO> iamUserWithPermissions) {
        if (iamUserWithPermissions.isEmpty()) {
            throw new AccessDeniedException();
        }
    }

    private void validateEmail(String email, UUID accountId) {
        if (iamUserRepository.existsByEmailIgnoreCaseAndAccountId(email, accountId)) {
            throw new EmailAlreadyExistsException("Email %s already exists".formatted(email));
        }
    }

    private ServiceRegister getServiceRegister() {
        return fleetHubProperties.getIam();
    }
}