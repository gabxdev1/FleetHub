package br.com.gabxdev.user.mapper;

import br.com.gabxdev.user.domain.User;
import br.com.gabxdev.user.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Mappings;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {

    @Mappings({
            @Mapping(target = "userId", source = "user.id"),
    })
    UserResponse toUserResponse(User user);
}
