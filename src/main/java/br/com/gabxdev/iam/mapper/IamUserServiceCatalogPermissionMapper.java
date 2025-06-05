package br.com.gabxdev.iam.mapper;

import br.com.gabxdev.iam.domain.pk.IamUserServiceCatalogPermissionId;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IamUserServiceCatalogPermissionMapper {

    IamUserServiceCatalogPermissionId toId(Long userId, Long serviceCatalogId);
}
