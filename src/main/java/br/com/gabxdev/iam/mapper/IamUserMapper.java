package br.com.gabxdev.iam.mapper;

import br.com.gabxdev.iam.domain.IamUser;
import br.com.gabxdev.iam.request.IamUserPostRequest;
import br.com.gabxdev.iam.response.IamUserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface IamUserMapper {

    @Mappings({
            @Mapping(target = "role", expression = "java(br.com.gabxdev.core.common.Role.IAM.toString())"),
            @Mapping(target = "password", expression = "java(request.password().trim())"),
            @Mapping(target = "email", expression = "java(request.email().trim())")
    })
    IamUser toEntity(IamUserPostRequest request);

    @Mappings({
            @Mapping(target = "iamUserId", source = "iamUser.id"),
            @Mapping(target = "accountId", source = "iamUser.accountId")
    })
    IamUserResponse toIamUserResponse(IamUser iamUser);
}
