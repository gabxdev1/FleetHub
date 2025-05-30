package br.com.gabxdev.iam.domain;

import br.com.gabxdev.core.audit.Auditable;
import br.com.gabxdev.iam.domain.enums.PermissionLevel;
import br.com.gabxdev.iam.domain.pk.IamUserServiceCatalogPermissionId;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_iam_user_service_catalog_permission")
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class IamUserServiceCatalogPermission extends Auditable {

    @EmbeddedId
    private IamUserServiceCatalogPermissionId id;

    @ManyToOne
    @MapsId("userId")
    private IamUser iamUser;

    @ManyToOne
    @MapsId("serviceCatalogId")
    private ServiceCatalog serviceCatalog;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PermissionLevel permissionLevel;
}
