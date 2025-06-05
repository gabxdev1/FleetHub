package br.com.gabxdev.iam.repository;

import br.com.gabxdev.iam.domain.IamUserServiceCatalogPermission;
import br.com.gabxdev.iam.domain.pk.IamUserServiceCatalogPermissionId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IamUserServiceCatalogPermissionRepository extends JpaRepository<IamUserServiceCatalogPermission, IamUserServiceCatalogPermissionId> {
}
