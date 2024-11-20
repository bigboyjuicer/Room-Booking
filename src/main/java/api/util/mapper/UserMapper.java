package api.util.mapper;

import api.dto.UserDto;
import api.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDto userDTO);

    @InheritInverseConfiguration
    UserDto fromUser(User user);

    List<User> toUsers(List<UserDto> userDtoList);

    List<UserDto> fromUsers(List<User> users);
}
