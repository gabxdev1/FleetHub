package br.com.gabxdev.iam.service;

import br.com.gabxdev.core.exception.AccessDeniedException;
import br.com.gabxdev.core.exception.ForbiddenException;
import br.com.gabxdev.iam.domain.IamUser;
import br.com.gabxdev.iam.domain.IamUserServiceCatalogPermission;
import br.com.gabxdev.iam.domain.enums.PermissionLevel;
import br.com.gabxdev.iam.domain.pk.IamUserServiceCatalogPermissionId;
import br.com.gabxdev.iam.mapper.IamUserServiceCatalogPermissionMapper;
import br.com.gabxdev.iam.repository.IamUserServiceCatalogPermissionRepository;
import br.com.gabxdev.iam.request.ServiceCatalogPermPostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class IamUserServiceCatalogPermissionService {

    private final IamUserServiceCatalogPermissionRepository repository;

    private final ServiceCatalogService serviceCatalogService;

    private final IamUserServiceCatalogPermissionMapper mapper;

    public void save(IamUser iamUser, List<ServiceCatalogPermPostRequest> serviceCatalogPermList) {
        if (Objects.isNull(serviceCatalogPermList)) {
            return;
        }

        serviceCatalogPermList.forEach(perm -> {
            var id = mapper.toId(iamUser.getId(), perm.serviceCatalogId());

            assertNotExists(id);

            var serviceCatalog = serviceCatalogService.findByIdOrThrowNotFound(perm.serviceCatalogId());

            var IamUserServiceCatalogPermissionToSave = IamUserServiceCatalogPermission
                    .builder()
                    .id(id)
                    .iamUser(iamUser)
                    .serviceCatalog(serviceCatalog)
                    .permissionLevel(perm.permissionLevel())
                    .build();

            repository.save(IamUserServiceCatalogPermissionToSave);
        });
    }

    private void assertNotExists(IamUserServiceCatalogPermissionId id) {
        if (repository.existsById(id)) {
            throw new ForbiddenException("IamUserServiceCatalogPermission already exists");
        }
    }

    public boolean hasPermission(Long currentUserId, Long serviceCatalogId, PermissionLevel requiredPermission) {
        var id = IamUserServiceCatalogPermissionId
                .builder()
                .userId(currentUserId)
                .serviceCatalogId(serviceCatalogId)
                .build();

        var iamUserServiceCatalogPermission = repository.findById(id)
                .orElseThrow(AccessDeniedException::new);

        return iamUserServiceCatalogPermission.getPermissionLevel().getValue() >= requiredPermission.getValue();
    }
}
