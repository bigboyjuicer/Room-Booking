package api.util;

import api.dto.UserDTO;
import api.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface UserMapper {
    UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    User toUser(UserDTO userDTO);

    @InheritInverseConfiguration
    UserDTO fromUser(User user);

    List<User> toUsers(List<UserDTO> userDTOList);

    List<UserDTO> fromUsers(List<User> users);
}
