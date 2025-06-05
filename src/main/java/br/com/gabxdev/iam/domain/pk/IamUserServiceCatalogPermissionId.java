package br.com.gabxdev.iam.domain.pk;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IamUserServiceCatalogPermissionId implements Serializable {
    private Long userId;

    private Long serviceCatalogId;
}
