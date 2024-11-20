package api.util.mapper;

import api.dto.RegisterUserDto;
import api.entity.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface RegisterUserMapper {

    RegisterUserMapper MAPPER = Mappers.getMapper(RegisterUserMapper.class);

    User toUser(RegisterUserDto registerUserDto);

    @InheritInverseConfiguration
    RegisterUserDto fromUser(User user);

    List<User> toUsers(List<RegisterUserDto> registerUserDtos);

    List<RegisterUserDto> fromUsers(List<User> users);
}
