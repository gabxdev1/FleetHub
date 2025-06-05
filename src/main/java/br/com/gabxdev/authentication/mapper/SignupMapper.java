package br.com.gabxdev.authentication.mapper;

import br.com.gabxdev.authentication.request.SignupRequest;
import br.com.gabxdev.user.domain.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface SignupMapper {

    @Mappings({
            @Mapping(target = "role", expression = "java(br.com.gabxdev.core.common.Role.OWNER.toString())"),
            @Mapping(target = "password", expression = "java(request.password().trim())"),
            @Mapping(target = "email", expression = "java(request.email().trim())"),
            @Mapping(target = "accountId", expression = "java(java.util.UUID.randomUUID())")
    })
    User toAccountUser(SignupRequest request);
}
